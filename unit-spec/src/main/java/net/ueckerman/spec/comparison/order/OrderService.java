package net.ueckerman.spec.comparison.order;

import net.ueckerman.spec.comparison.product.Product;
import net.ueckerman.spec.comparison.user.User;

import java.util.List;

/**
 * A facade performing order specific logic.
 */
public interface OrderService {

    Order placeOrderFor(List<Product> products, User user);

}
