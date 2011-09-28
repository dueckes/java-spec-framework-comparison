package net.ueckerman.payment;

import net.ueckerman.order.Order;

/**
 * A strategy for interacting with payment service providers.
 */
public interface PaymentGateway {

    void payFor(Order order);

}
