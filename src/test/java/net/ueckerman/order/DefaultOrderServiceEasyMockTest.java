package net.ueckerman.order;

import net.ueckerman.payment.PaymentGateway;
import net.ueckerman.product.Product;
import net.ueckerman.user.User;
import org.easymock.EasyMockSupport;
import org.easymock.IArgumentMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static net.ueckerman.order.OrderMother.createOrder;
import static net.ueckerman.product.ProductMother.createProducts;
import static net.ueckerman.user.UserMother.createUser;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DefaultOrderServiceEasyMockTest extends EasyMockSupport {

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
        orderFactory = createStrictMock(OrderFactory.class);
        orderRepository = createStrictMock(OrderRepository.class);
        paymentGateway = createStrictMock(PaymentGateway.class);

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
        replayAll();

        defaultOrderService.placeOrderFor(products, user);

        verifyAll();
    }

    @Test
    public void placeOrderForChargesForSavedOrderViaPaymentGateway() {
        paymentGateway.payFor(savedOrder);
        replayAll();

        defaultOrderService.placeOrderFor(products, user);

        verifyAll();
    }

    @Test
    public void placeOrderForChargesAnUnpaidOrder() {
        paymentGateway.payFor(isOrderWithStatus(OrderStatus.NEW));
        replayAll();

        defaultOrderService.placeOrderFor(products, user);

        verifyAll();
    }

    @Test
    public void placeOrderForChargesForSavedOrderAfterNewOrderIsPersisted() {
        expect(orderRepository.save(unsavedOrder)).andReturn(savedOrder);
        paymentGateway.payFor(savedOrder);
        replayAll();

        defaultOrderService.placeOrderFor(products, user);

        verifyAll();
    }

    @Test
    public void placeOrderPersistsPaidOrderAfterOrderIsPaid() {
        paymentGateway.payFor(savedOrder);
        Order secondSavedOrder = createOrder();
        expect(orderRepository.save(savedOrder)).andReturn(secondSavedOrder);
        replayAll();

        defaultOrderService.placeOrderFor(products, user);

        verifyAll();
    }

    @Test
    public void placeOrderForReturnsLastSavedOrder() {
        Order secondSavedOrder = createOrder();
        expect(orderRepository.save(savedOrder)).andReturn(secondSavedOrder);
        replayAll();

        assertThat(secondSavedOrder, is(equalTo(defaultOrderService.placeOrderFor(products, user))));

        verifyAll();
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
