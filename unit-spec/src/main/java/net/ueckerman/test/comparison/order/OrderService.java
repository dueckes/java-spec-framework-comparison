package net.ueckerman.test.comparison.order;

import net.ueckerman.test.comparison.product.Product;
import net.ueckerman.test.comparison.user.User;

import java.util.List;

/**
 * A facade performing order specific logic.
 */
public interface OrderService {

    Order placeOrderFor(List<Product> products, User user);

}
