package net.ueckerman.spec.comparison.order;

import net.ueckerman.spec.comparison.product.Product;
import net.ueckerman.spec.comparison.user.User;

import java.util.List;

/**
 * Constructs orders.
 */
public interface OrderFactory {

    Order create(List<Product> products, User user);

}
