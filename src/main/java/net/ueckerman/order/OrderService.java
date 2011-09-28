package net.ueckerman.order;

import net.ueckerman.product.Product;
import net.ueckerman.user.User;

import java.util.List;

/**
 * A facade performing order specific logic.
 */
public interface OrderService {

    Order placeOrderFor(List<Product> products, User user);

}
