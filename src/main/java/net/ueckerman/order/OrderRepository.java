package net.ueckerman.order;

/**
 * Manages persistent orders.
 */
public interface OrderRepository {

    Order save(Order order);

}
