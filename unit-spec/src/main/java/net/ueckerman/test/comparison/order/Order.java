package net.ueckerman.test.comparison.order;

import net.ueckerman.test.comparison.finance.Money;
import net.ueckerman.test.comparison.product.Product;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * An order for a product.
 */
public class Order {

    private final Integer id;
    private final List<Product> products;

    private OrderStatus status;

    private Order(Integer id, List<Product> products, OrderStatus status) {
        Validate.notEmpty(products, "At least one product must be provided");
        this.id = id;
        this.products = products;
        this.status = status;
    }

    public Order(List<Product> products) {
        this(null, products, OrderStatus.NEW);
    }

    public Order copyWithId(Integer id) {
        return new Order(id, products, status);
    }

    public Money totalCost() {
        Money total = new Money(0);
        for (Product product : products) {
            total = total.add(product.getCost());
        }
        return total;
    }

    public Integer getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    void transitionToStatus(OrderStatus target) {
        if (!target.isValidPriorStatus(status)) {
            throw new IllegalOrderStatusTransitionException(status, target);
        }
        this.status = target;
    }

}
