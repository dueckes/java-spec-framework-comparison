package net.ueckerman.spec.comparison.order;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import net.ueckerman.spec.comparison.payment.PaymentGateway;
import net.ueckerman.spec.comparison.product.Product;
import net.ueckerman.spec.comparison.user.User;
import org.easymock.IArgumentMatcher;
import org.easymock.IMocksControl;
import org.junit.runner.RunWith;

import java.util.List;

import static net.ueckerman.spec.comparison.product.ProductMother.createProducts;
import static net.ueckerman.spec.comparison.user.UserMother.createUser;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;

@RunWith(JDaveRunner.class)
public class DefaultOrderServiceEasyMockJDaveSpec extends Specification<DefaultOrderService> {

    private IMocksControl control = createStrictControl();

    private OrderRepository orderRepository;
    private OrderFactory orderFactory;
    private PaymentGateway paymentGateway;

    private List<Product> products;
    private User user;
    private Order unsavedOrder;
    private Order savedOrder;

    private DefaultOrderService defaultOrderService;

    @Override
    public void create() {
        control = createStrictControl();
        orderFactory = control.createMock(OrderFactory.class);
        orderRepository = control.createMock(OrderRepository.class);
        paymentGateway = control.createMock(PaymentGateway.class);

        products = createProducts();
        user = createUser();
        unsavedOrder = OrderMother.createOrder();
        savedOrder = OrderMother.createOrder();

        defaultOrderService = new DefaultOrderService(orderFactory, orderRepository, paymentGateway);

        expect(orderFactory.create(products, user)).andStubReturn(unsavedOrder);
        expect(orderRepository.save(anyObject(Order.class))).andStubReturn(savedOrder);
        paymentGateway.payFor(anyObject(Order.class));
        expectLastCall().asStub();
    }

    public class PlaceOrderFor {

        public void shouldPersistACreatedOrder() {
            expect(orderRepository.save(unsavedOrder)).andReturn(savedOrder);
            control.replay();

            defaultOrderService.placeOrderFor(products, user);

            control.verify();
        }

        public void shouldChargeForASavedOrderViaPaymentGateway() {
            paymentGateway.payFor(savedOrder);
            control.replay();

            defaultOrderService.placeOrderFor(products, user);

            control.verify();
        }

        public void shouldChargeForAnUnpaidOrder() {
            paymentGateway.payFor(isOrderWithStatus(OrderStatus.NEW));
            control.replay();

            defaultOrderService.placeOrderFor(products, user);

            control.verify();
        }

        public void shouldChargeForTheSavedOrderAfterTheNewOrderIsPersisted() {
            expect(orderRepository.save(unsavedOrder)).andReturn(savedOrder);
            paymentGateway.payFor(savedOrder);
            control.replay();

            defaultOrderService.placeOrderFor(products, user);

            control.verify();
        }

        public void shouldPersistThePaidOrderAfterTheOrderIsPaid() {
            paymentGateway.payFor(savedOrder);
            Order secondSavedOrder = OrderMother.createOrder();
            expect(orderRepository.save(savedOrder)).andReturn(secondSavedOrder);
            control.replay();

            defaultOrderService.placeOrderFor(products, user);

            control.verify();
        }

        public void shouldReturnTheLastPersistedOrder() {
            Order secondSavedOrder = OrderMother.createOrder();
            expect(orderRepository.save(savedOrder)).andReturn(secondSavedOrder);
            control.replay();

            specify(secondSavedOrder, should.equal(defaultOrderService.placeOrderFor(products, user)));

            control.verify();
        }

    }

    private Order isOrderWithStatus(OrderStatus orderStatus) {
        reportMatcher(new OrderWithStatusMatcher(orderStatus));
        return null;
    }

    private static class OrderWithStatusMatcher implements IArgumentMatcher {

        private final OrderStatus expectedOrderStatus;

        public OrderWithStatusMatcher(OrderStatus expectedOrderStatus) {
            this.expectedOrderStatus = expectedOrderStatus;
        }

        public boolean matches(Object argument) {
            return ((Order) argument).getStatus() == expectedOrderStatus;
        }

        public void appendTo(StringBuffer buffer) {
            buffer.append("Order Status: ").append(expectedOrderStatus.toString());
        }

    }

}
