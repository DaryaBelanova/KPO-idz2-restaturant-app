package hse.kpo.restaurant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Order {
    @Setter
    private Integer id;
    @Setter
    private Integer userId;
    private final List<Dish> dishes;
    @Getter
    private int timeToReady;
    @Setter
    private Status status;

    public enum Status {
        CREATED,
        PREPARING,
        CANCELED,
        READY,
        PAYED
    }

    public synchronized void increaseTimeToReady(int min) {
        timeToReady += min;
    }

    @JsonCreator
    public Order(Dish[] dishes) {
        this.dishes = Arrays.stream(dishes).toList();
        for (Dish dish : dishes) {
            increaseTimeToReady(dish.getDurationInMin());
        }
        status = Status.CREATED;
    }
    public boolean addDish(Dish dish) {
        increaseTimeToReady(dish.getDurationInMin());
        timeToReady += dish.getDurationInMin();
        status = Status.PREPARING;
        return dishes.add(dish);
    }

    public boolean isActive() {
        return status != Status.PREPARING;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", dishes=" + dishes +
                ", timeToReady=" + timeToReady +
                ", status=" + status +
                '}';
    }
}
