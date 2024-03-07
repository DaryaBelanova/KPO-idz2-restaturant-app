package hse.kpo.restaurant.service;

import hse.kpo.restaurant.model.Dish;
import hse.kpo.restaurant.model.Order;
import hse.kpo.restaurant.repository.DishRepository;
import hse.kpo.restaurant.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public String tryAdd(Order order, Integer userId) {
        for (Dish inOrder : order.getDishes()) {
            if (!dishRepository.contains(inOrder)) {
                return "ERROR: Some dish items are not available in menu";
            }
        }
        order.setUserId(userId);
        order.setStatus(Order.Status.CREATED);
        if (orderRepository.offer(order) == OrderRepository.Status.SUCCESS) {
            CookingService.cookOrder(order);
            return "Order is processing";
        }
        return "ERROR: Order was not created";
    }

    public String tryCancel(Order order, Integer userId) {
        if (order == null) {
            return "ERROR: Impossible to cancel non-existing order";
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            return "ERROR: Order belongs to other user";
        }
        if (!order.isActive()) {
            return "ERROR: It is impossible to cancel order that is not in process";
        }
        order.setStatus(Order.Status.CANCELED);
        if (orderRepository.contains(order)) {
            order.setStatus(Order.Status.CANCELED);
            return "Order successfully canceled";
        }
        return "ERROR: Order was not canceled";
    }

    public String tryAddDish(Dish dish, Order order, Integer userId) {
        if (order == null) {
            return "ERROR: Impossible to update non-existing order";
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            return "ERROR: Order belongs to other user";
        }
        if (order.isActive()) {
            return "ERROR: It is impossible to add dish to cooked order";
        }
        if (!orderRepository.contains(order)/*orderRepository.remove(order) == OrderRepository.Status.ERROR*/) {
            return "ERROR: Impossible to update non-existent order";
        }
        if (order.addDish(dish)) {
            return "Dish successfully added to order";
        }
        return "ERROR: Dish was nor added";
    }

    public String tryGetOrderStatus(Order order, Integer userId) {
        if (order == null || !orderRepository.contains(order)) {
            return "ERROR: Unable to get status of non-existent order";
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            return "ERROR: Order belongs to other user";
        }
        return order.getStatus().name();
    }

    public String tryPayCookedOrder(Order order, Integer userId) {
        if (order == null || !orderRepository.contains(order)) {
            return "ERROR: Unable to pay for non-existent order";
        }
        if (!Objects.equals(order.getUserId(), userId)) {
            return "ERROR: Order belongs to other user";
        }
        if (order.getStatus() != Order.Status.READY) {
            return "ERROR: Unable to pay for non-ready order";
        }
        order.setStatus(Order.Status.PAYED);
        return "Order successfully payed";
    }
    public Order getById(Integer id) {
        return orderRepository.getById(id);
    }

    public List<Order> getByUserId(Integer userId) {
        return orderRepository.getByUserId(userId);
    }
}
