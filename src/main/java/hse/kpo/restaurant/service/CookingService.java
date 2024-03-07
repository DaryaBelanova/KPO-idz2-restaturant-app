package hse.kpo.restaurant.service;

import hse.kpo.restaurant.model.Dish;
import hse.kpo.restaurant.model.Order;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CookingService {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void cookOrder(Order order) {
        executorService.submit(() -> {
            while (order.getStatus() != Order.Status.READY) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    switch (order.getStatus()) {
                        case CREATED:
                            order.setStatus(Order.Status.PREPARING);
                            break;
                        case PREPARING:
                            order.setStatus(Order.Status.READY);
                            break;
                        case CANCELED, PAYED:
                            break;
                    }
                } catch (InterruptedException ignored) {
                }
            }
        });
    }
}
