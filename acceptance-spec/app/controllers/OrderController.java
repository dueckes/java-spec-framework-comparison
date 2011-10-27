package controllers;

import net.ueckerman.test.comparison.finance.Money;
import net.ueckerman.test.comparison.order.InMemoryOrderRepository;
import net.ueckerman.test.comparison.order.Order;
import net.ueckerman.test.comparison.order.OrderRepository;
import net.ueckerman.test.comparison.product.Product;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OrderController extends Controller {

    private static final OrderRepository ORDER_REPOSITORY = new InMemoryOrderRepository();

    public static void index() {
        List<Order> orders = ORDER_REPOSITORY.findAll();
        render(orders);
    }

    public static void create() {
        ArrayList<Product> products = newArrayList(new Product("some product", new Money(2000)));
        Order order = ORDER_REPOSITORY.save(new Order(products));
        renderTemplate("OrderController/show.html", order);
    }

    public static void show(Integer id) {
        Order order = ORDER_REPOSITORY.find(id);
        render(order);
    }

}
