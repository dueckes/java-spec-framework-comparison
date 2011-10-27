package net.ueckerman.spec.comparison.order;

import net.ueckerman.spec.comparison.payment.PaymentGateway;
import net.ueckerman.spec.comparison.product.Product;
import net.ueckerman.spec.comparison.user.User;
import org.apache.commons.lang3.Validate;

import java.util.List;

public class DefaultOrderService implements OrderService {

    private final OrderFactory orderFactory;
    private final PaymentGateway paymentGateway;
    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderFactory orderFactory, OrderRepository orderRepository,
                               PaymentGateway paymentGateway) {
        Validate.notNull(orderFactory, "Order factory must be provided");
        Validate.notNull(orderRepository, "Order repository must be provided");
        Validate.notNull(paymentGateway, "Payment gateway must be provided");
        this.orderFactory = orderFactory;
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public Order placeOrderFor(List<Product> products, User user) {
        Order order = orderFactory.create(products, user);
        Order savedOrder = orderRepository.save(order);
        paymentGateway.payFor(savedOrder);
        savedOrder.transitionToStatus(OrderStatus.PAID);
        return orderRepository.save(savedOrder);
    }

}
