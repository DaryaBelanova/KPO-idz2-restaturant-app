package hse.kpo.restaurant.repository;

import hse.kpo.restaurant.model.Order;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Repository
public class OrderRepository {
    private final Queue<Order> orders = new LinkedBlockingQueue<>();

    public List<Order> getByUserId(Integer userId) {
        return orders.stream().filter(order -> order.getUserId().equals(userId)).toList();
    }

    public enum Status {
        ERROR,
        SUCCESS
    }

    public Status offer(Order order) {
        if (orders.offer(order)) {
            return Status.SUCCESS;
        }
        return Status.ERROR;
    }

    public boolean contains(Order order) {
        if (order == null) {
            return false;
        }
        return orders.contains(order);
    }

    public Status remove(Order order) {
        if (orders.remove(order)) {
            return Status.SUCCESS;
        }
        return Status.ERROR;
    }

    public Order getById(Integer id) {
        return orders.stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }
}
