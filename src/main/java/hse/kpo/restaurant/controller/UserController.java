package hse.kpo.restaurant.controller;

import hse.kpo.restaurant.model.Dish;
import hse.kpo.restaurant.model.Order;
import hse.kpo.restaurant.model.User;
import hse.kpo.restaurant.service.DishService;
import hse.kpo.restaurant.service.OrderService;
import hse.kpo.restaurant.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private OrderService orderService;
    private DishService dishService;

    private final AtomicInteger counter = new AtomicInteger(1);

    @PostMapping("order/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order, @RequestParam String login) {
        String authResponse = userService.authenticate(login);
        User user = userService.getByLogin(login);
        order.setUserId(user.getId());
        order.setId(counter.getAndIncrement());
        if (authResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(authResponse);
        }
        String addResponse = orderService.tryAdd(order, user.getId());
        if (addResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(addResponse);
        }
        return ResponseEntity.ok(addResponse);
    }

    @GetMapping("order/list")
    public ResponseEntity<String> getAllOrders(@RequestParam String login) {
        String authResponse = userService.authenticate(login);
        User user = userService.getByLogin(login);
        if (authResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(authResponse);
        }
        return ResponseEntity.ok(orderService.getByUserId(user.getId()).toString());
    }

    @PostMapping("order/update")
    public ResponseEntity<String> updateOrder(@RequestBody Dish dish, @RequestParam String orderId, @RequestParam String login) {
        String authResponse = userService.authenticate(login);
        User user = userService.getByLogin(login);
        if (authResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(authResponse);
        }
        Integer intId = null;
        try {
            intId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid param value");
        }
        Order toUpdate = orderService.getById(intId);
        String addResponse = orderService.tryAddDish(dish, toUpdate, user.getId());
        if (addResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(addResponse);
        }
        return ResponseEntity.ok(addResponse);
    }

    @PostMapping("order/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam String orderId, @RequestParam String login) {
        String authResponse = userService.authenticate(login);
        User user = userService.getByLogin(login);
        if (authResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(authResponse);
        }
        Integer intId = null;
        try {
            intId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid param value");
        }
        Order toCancel = orderService.getById(intId);
        String addResponse = orderService.tryCancel(toCancel, user.getId());
        if (addResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(addResponse);
        }
        return ResponseEntity.ok(addResponse);
    }

    @GetMapping("order/status")
    public ResponseEntity<String> getOrderStatus(@RequestParam String orderId, @RequestParam String login) {
        String authResponse = userService.authenticate(login);
        User user = userService.getByLogin(login);
        if (authResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(authResponse);
        }
        Integer intId = null;
        try {
            intId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid param value");
        }
        Order toCheckStatus = orderService.getById(intId);
        String addResponse = orderService.tryGetOrderStatus(toCheckStatus, user.getId());
        if (addResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(addResponse);
        }
        return ResponseEntity.ok(addResponse);
    }

    @PostMapping("order/pay")
    public ResponseEntity<String> payForReadyOrder(@RequestParam String orderId, @RequestParam String login) {
        String authResponse = userService.authenticate(login);
        User user = userService.getByLogin(login);
        if (authResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(authResponse);
        }
        Integer intId = null;
        try {
            intId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid param value");
        }
        Order toPay = orderService.getById(intId);
        String payResponse = orderService.tryPayCookedOrder(toPay, user.getId());
        if (payResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(payResponse);
        }
        return ResponseEntity.ok(payResponse);
    }

    @GetMapping("menu")
    public List<Dish> getAllDishItems()
    {
        return dishService.getAll();
    }
}
