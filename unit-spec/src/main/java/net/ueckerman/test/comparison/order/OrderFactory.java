package net.ueckerman.test.comparison.order;

import net.ueckerman.test.comparison.product.Product;
import net.ueckerman.test.comparison.user.User;

import java.util.List;

/**
 * Constructs orders.
 */
public interface OrderFactory {

    Order create(List<Product> products, User user);

}
