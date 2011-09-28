package net.ueckerman.order;

import net.ueckerman.order.Order;
import net.ueckerman.product.ProductMother;

/**
 * Contructs orders for test purposes.
 */
public class OrderMother {

    public OrderMother() {
        // Static class
    }

    public static Order createOrder() {
        return new Order(ProductMother.createProducts());
    }

}
