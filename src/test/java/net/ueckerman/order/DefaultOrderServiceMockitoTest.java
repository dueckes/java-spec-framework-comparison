package net.ueckerman.order;

import net.ueckerman.payment.PaymentGateway;
import net.ueckerman.product.Product;
import net.ueckerman.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.List;

import static net.ueckerman.order.OrderMother.createOrder;
import static net.ueckerman.product.ProductMother.createProducts;
import static net.ueckerman.user.UserMother.createUser;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultOrderServiceMockitoTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderFactory orderFactory;
    @Mock
    private PaymentGateway paymentGateway;

    private List<Product> products;
    private User user;
    private Order unsavedOrder;
    private Order savedOrder;

    private DefaultOrderService defaultOrderService;

    @Before
    public void setUp() {
        initMocks(this);

        products = createProducts();
        user = createUser();
        unsavedOrder = createOrder();
        savedOrder = createOrder();

        defaultOrderService = new DefaultOrderService(orderFactory, orderRepository, paymentGateway);

        given(orderFactory.create(products, user)).willReturn(unsavedOrder);
        given(orderRepository.save(any(Order.class))).willReturn(savedOrder);
    }

    @Test
    public void placeOrderForPersistsCreatedOrder() {
        defaultOrderService.placeOrderFor(products, user);

        verify(orderRepository).save(unsavedOrder);
    }

    @Test
    public void placeOrderForChargesForSavedOrderViaPaymentGateway() {
        defaultOrderService.placeOrderFor(products, user);

        verify(paymentGateway).payFor(savedOrder);
    }

    @Test
    public void placeOrderForChargesAnUnpaidOrder() {
        RuntimeException forcedException = new RuntimeException("Invalid Order Status");
        doThrow(forcedException).when(paymentGateway).payFor(argThat(isOrderWithStatus(OrderStatus.NEW)));

        try {
            defaultOrderService.placeOrderFor(products, user);
            fail(RuntimeException.class.getName() + " expected");
        } catch (RuntimeException runtimeExc) {
            assertThat(runtimeExc, is(sameInstance(forcedException)));
        }
    }

    @Test
    public void placeOrderForChargesForSavedOrderAfterNewOrderIsPersisted() {
        defaultOrderService.placeOrderFor(products, user);

        InOrder inOrder = inOrder(orderRepository, paymentGateway);

        inOrder.verify(orderRepository).save(unsavedOrder);
        inOrder.verify(paymentGateway).payFor(savedOrder);
    }

    @Test
    public void placeOrderPersistsPaidOrderAfterOrderIsPersisted() {
        defaultOrderService.placeOrderFor(products, user);

        InOrder inOrder = inOrder(paymentGateway, orderRepository);

        inOrder.verify(paymentGateway).payFor(savedOrder);
        inOrder.verify(orderRepository).save(savedOrder);
    }

    @Test
    public void placeOrderForReturnsLastSavedOrder() {
        Order secondSavedOrder = createOrder();
        given(orderRepository.save(savedOrder)).willReturn(secondSavedOrder);

        assertThat(secondSavedOrder, is(equalTo(defaultOrderService.placeOrderFor(products, user))));
    }

    private ArgumentMatcher<Order> isOrderWithStatus(OrderStatus orderStatus) {
        return new OrderWithStatusMatcher(orderStatus);
    }

    private static final class OrderWithStatusMatcher extends ArgumentMatcher<Order> {

        private final OrderStatus expectedOrderStatus;

        public OrderWithStatusMatcher(OrderStatus expectedOrderStatus) {
            this.expectedOrderStatus = expectedOrderStatus;
        }

        @Override
        public boolean matches(Object argument) {
            return ((Order) argument).getStatus() == expectedOrderStatus;
        }
    }

}
