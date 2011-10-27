package net.ueckerman.spec.comparison.order;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import net.ueckerman.spec.comparison.payment.PaymentGateway;
import net.ueckerman.spec.comparison.product.Product;
import net.ueckerman.spec.comparison.user.User;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.List;

import static net.ueckerman.spec.comparison.product.ProductMother.createProducts;
import static net.ueckerman.spec.comparison.user.UserMother.createUser;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JDaveRunner.class)
public class DefaultOrderServiceMockitoJDaveSpec extends Specification<DefaultOrderService> {

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

    @Override
    public void create() {
        initMocks(this);

        products = createProducts();
        user = createUser();
        unsavedOrder = OrderMother.createOrder();
        savedOrder = OrderMother.createOrder();

        defaultOrderService = new DefaultOrderService(orderFactory, orderRepository, paymentGateway);

        given(orderFactory.create(products, user)).willReturn(unsavedOrder);
        given(orderRepository.save(any(Order.class))).willReturn(savedOrder);
    }

    public class PlaceOrderFor {

        public void shouldPersistTheCreatedOrder() {
            defaultOrderService.placeOrderFor(products, user);

            verify(orderRepository).save(unsavedOrder);
        }

        public void shouldChargeForASavedOrderViaPaymentGateway() {
            defaultOrderService.placeOrderFor(products, user);

            verify(paymentGateway).payFor(savedOrder);
        }

        public void shouldChargeForANewOrder() {
            RuntimeException forcedException = new RuntimeException("Invalid Order Status");
            doThrow(forcedException).when(paymentGateway).payFor(argThat(isOrderWithStatus(OrderStatus.NEW)));

            try {
                defaultOrderService.placeOrderFor(products, user);
                fail(RuntimeException.class.getName() + " expected");
            } catch (RuntimeException runtimeExc) {
                specify(runtimeExc, is(sameInstance(forcedException)));
            }
        }

        public void shouldChargeForSavedOrderAfterANewOrderIsPersisted() {
            defaultOrderService.placeOrderFor(products, user);

            InOrder inOrder = inOrder(orderRepository, paymentGateway);

            inOrder.verify(orderRepository).save(unsavedOrder);
            inOrder.verify(paymentGateway).payFor(savedOrder);
        }

        public void shouldPersistAPaidOrderAfterAnOrderIsPersisted() {
            defaultOrderService.placeOrderFor(products, user);

            InOrder inOrder = inOrder(paymentGateway, orderRepository);

            inOrder.verify(paymentGateway).payFor(savedOrder);
            inOrder.verify(orderRepository).save(savedOrder);
        }

        public void shouldReturnTheLastPersistedOrder() {
            Order secondSavedOrder = OrderMother.createOrder();
            given(orderRepository.save(savedOrder)).willReturn(secondSavedOrder);

            specify(secondSavedOrder, should.equal(defaultOrderService.placeOrderFor(products, user)));
        }

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
