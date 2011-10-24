package net.ueckerman.test.comparison.order;

import net.ueckerman.test.comparison.product.ProductMother;

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
