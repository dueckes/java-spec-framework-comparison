package net.ueckerman.test.comparison.payment;

import net.ueckerman.test.comparison.order.Order;

/**
 * A strategy for interacting with payment service providers.
 */
public interface PaymentGateway {

    void payFor(Order order);

}
