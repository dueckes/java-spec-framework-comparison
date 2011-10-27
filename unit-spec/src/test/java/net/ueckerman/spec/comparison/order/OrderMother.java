package net.ueckerman.spec.comparison.order;

import net.ueckerman.spec.comparison.repository.SequentialIdGenerator;
import net.ueckerman.spec.comparison.product.ProductMother;

/**
 * Constructs orders for test purposes.
 */
public class OrderMother {

    private static final SequentialIdGenerator ID_GENERATOR = new SequentialIdGenerator();

    public OrderMother() {
        // Static class
    }

    public static Order createOrder() {
        return new Order(ProductMother.createProducts());
    }

    public static Order createPersistedOrder() {
        Order order = new Order(ProductMother.createProducts());
        order.copyWithId(ID_GENERATOR.next());
        return order;
    }

}
