package net.ueckerman.order;

import net.ueckerman.finance.Money;
import net.ueckerman.product.Product;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * An order for a product.
 */
public class Order {

    private final List<Product> products;

    private OrderStatus status;

    public Order(List<Product> products) {
        Validate.notEmpty(products, "At least one product must be provided");
        this.products = products;
        this.status = OrderStatus.NEW;
    }

    public Money totalCost() {
        Money total = new Money(0);
        for (Product product : products) {
            total = total.add(product.getCost());
        }
        return total;
    }

    OrderStatus getStatus() {
        return status;
    }

    void transitionToStatus(OrderStatus target) {
        if (!target.isValidPriorStatus(status)) {
            throw new IllegalOrderStatusTransitionException(status, target);
        }
        this.status = target;
    }

}
