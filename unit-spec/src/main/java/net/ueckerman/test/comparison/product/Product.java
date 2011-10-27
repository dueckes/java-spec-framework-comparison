package net.ueckerman.test.comparison.product;

import net.ueckerman.test.comparison.finance.Money;
import org.apache.commons.lang3.Validate;

/**
 * A sellable product.
 */
public class Product {

    private final String description;
    private final Money cost;

    public Product(String description, Money cost) {
        Validate.notEmpty(description, "Product description must be provided");
        Validate.notNull(cost, "Product cost must be provided");
        this.description = description;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public Money getCost() {
        return cost;
    }

}
