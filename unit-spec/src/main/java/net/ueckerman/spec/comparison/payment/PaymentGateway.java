package net.ueckerman.spec.comparison.payment;

import net.ueckerman.spec.comparison.order.Order;

/**
 * A strategy for interacting with payment service providers.
 */
public interface PaymentGateway {

    void payFor(Order order);

}
