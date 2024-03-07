package hse.kpo.restaurant.repository;

import hse.kpo.restaurant.model.Dish;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DishRepository {
    private final List<Dish> dishes = new ArrayList<>(Arrays.asList(
            new Dish(1, "Pizza Margarita", 15, 300),
            new Dish(2, "Salad Caesar", 10, 300),
            new Dish(3, "Mohito", 5, 100)
    ));

    public enum Status {
        ERROR,
        SUCCESS
    }

    public List<Dish> getAll() {
        return new ArrayList<>(dishes);
    }

    public Status add(Dish dish) {
        if (dishes.add(dish)) {
            return Status.SUCCESS;
        }
        return Status.ERROR;
    }

    public Status delete(Dish dish) {
        if (dishes.remove(dish)) {
            return Status.SUCCESS;
        }
        return Status.ERROR;
    }

    public boolean contains(Dish dish) {
        return dishes.contains(dish);
    }

    public Dish getById(Integer id) {
        return dishes.stream().filter(dish -> dish.getId().equals(id)).findFirst().orElse(null);
    }
}
