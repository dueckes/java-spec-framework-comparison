package net.ueckerman.order;

import net.ueckerman.payment.PaymentGateway;
import net.ueckerman.product.Product;
import net.ueckerman.user.User;
import org.easymock.IArgumentMatcher;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static net.ueckerman.order.OrderMother.createOrder;
import static net.ueckerman.product.ProductMother.createProducts;
import static net.ueckerman.user.UserMother.createUser;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DefaultOrderServiceEasyMockTest {

    private IMocksControl control = createStrictControl();

    private OrderRepository orderRepository;
    private OrderFactory orderFactory;
    private PaymentGateway paymentGateway;

    private List<Product> products;
    private User user;
    private Order unsavedOrder;
    private Order savedOrder;

    private DefaultOrderService defaultOrderService;

    @Before
    public void setUp() {
        control = createStrictControl();
        orderFactory = control.createMock(OrderFactory.class);
        orderRepository = control.createMock(OrderRepository.class);
        paymentGateway = control.createMock(PaymentGateway.class);

        products = createProducts();
        user = createUser();
        unsavedOrder = createOrder();
        savedOrder = createOrder();

        defaultOrderService = new DefaultOrderService(orderFactory, orderRepository, paymentGateway);

        expect(orderFactory.create(products, user)).andStubReturn(unsavedOrder);
        expect(orderRepository.save(anyObject(Order.class))).andStubReturn(savedOrder);
        paymentGateway.payFor(anyObject(Order.class));
        expectLastCall().asStub();
    }

    @Test
    public void placeOrderForPersistsCreatedOrder() {
        expect(orderRepository.save(unsavedOrder)).andReturn(savedOrder);
        control.replay();

        defaultOrderService.placeOrderFor(products, user);

        control.verify();
    }

    @Test
    public void placeOrderForChargesForSavedOrderViaPaymentGateway() {
        paymentGateway.payFor(savedOrder);
        control.replay();

        defaultOrderService.placeOrderFor(products, user);

        control.verify();
    }

    @Test
    public void placeOrderForChargesAnUnpaidOrder() {
        paymentGateway.payFor(isOrderWithStatus(OrderStatus.NEW));
        control.replay();

        defaultOrderService.placeOrderFor(products, user);

        control.verify();
    }

    @Test
    public void placeOrderForChargesForSavedOrderAfterNewOrderIsPersisted() {
        expect(orderRepository.save(unsavedOrder)).andReturn(savedOrder);
        paymentGateway.payFor(savedOrder);
        control.replay();

        defaultOrderService.placeOrderFor(products, user);

        control.verify();
    }

    @Test
    public void placeOrderPersistsPaidOrderAfterOrderIsPaid() {
        paymentGateway.payFor(savedOrder);
        Order secondSavedOrder = createOrder();
        expect(orderRepository.save(savedOrder)).andReturn(secondSavedOrder);
        control.replay();

        defaultOrderService.placeOrderFor(products, user);

        control.verify();
    }

    @Test
    public void placeOrderForReturnsLastSavedOrder() {
        Order secondSavedOrder = createOrder();
        expect(orderRepository.save(savedOrder)).andReturn(secondSavedOrder);
        control.replay();

        assertThat(secondSavedOrder, is(equalTo(defaultOrderService.placeOrderFor(products, user))));

        control.verify();
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
