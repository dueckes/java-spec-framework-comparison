package net.ueckerman.order;

import net.ueckerman.product.Product;
import net.ueckerman.user.User;

import java.util.List;

/**
 * Constructs orders.
 */
public interface OrderFactory {

    Order create(List<Product> products, User user);

}
