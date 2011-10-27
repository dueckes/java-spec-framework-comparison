package net.ueckerman.spec.comparison.order;

import java.util.List;

/**
 * Manages persistent orders.
 */
public interface OrderRepository {

    Order save(Order order);

    Order find(Integer id);

    List<Order> findAll();

}
