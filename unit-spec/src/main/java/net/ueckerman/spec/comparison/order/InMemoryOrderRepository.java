package net.ueckerman.spec.comparison.order;

import net.ueckerman.spec.comparison.repository.SequentialIdGenerator;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

public class InMemoryOrderRepository implements OrderRepository {

    private final SequentialIdGenerator idGenerator;
    private final LinkedHashMap<Integer, Order> savedOrders;

    public InMemoryOrderRepository() {
        this.idGenerator = new SequentialIdGenerator();
        this.savedOrders = newLinkedHashMap();
    }

    @Override
    public Order save(Order order) {
        Order savedOrder = order.copyWithId(idGenerator.next());
        savedOrders.put(savedOrder.getId(), savedOrder);
        return savedOrder;
    }

    @Override
    public Order find(Integer id) {
        Order foundOrder = savedOrders.get(id);
        Validate.notNull(foundOrder, "No order found with id %s", id);
        return foundOrder;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = newArrayList();
        for (Map.Entry<Integer, Order> entry : savedOrders.entrySet()) {
            orders.add(entry.getValue());
        }
        return orders;
    }

}
