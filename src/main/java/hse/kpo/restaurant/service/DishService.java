package hse.kpo.restaurant.service;

import hse.kpo.restaurant.model.Dish;
import hse.kpo.restaurant.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {
    private DishRepository dishRepository;
    public String addDishItem(Dish dish) {
        if (dishRepository.contains(dish)) {
            return "ERROR: Dish item was already added";
        }
        if (dishRepository.add(dish) == DishRepository.Status.SUCCESS) {
            return "New dish item successfully added";
        }
        return "ERROR: Dish item was not added";
    }

    public String removeDishItem(Dish dish) {
        if (dish == null || getById(dish.getId()) == null) {
            return "ERROR: Dish to delete not found";
        }
        if (dishRepository.delete(dish) == DishRepository.Status.SUCCESS) {
            return "Dish item successfully deleted";
        }
        return "ERROR: Dish item was not deleted";
    }

    public String updateDishItem(Dish dish, Integer durationInMin, Integer price) {
        if (dish == null || getById(dish.getId()) == null) {
            return "ERROR: Dish to update not found";
        }
        if (durationInMin != null) {
            dish.setDurationInMin(durationInMin);
        }
        if (price != null) {
            dish.setPrice(price);
        }
        return "Dish successfully updated";

    }

    public Dish getById(Integer id) {
        return dishRepository.getById(id);
    }

    public List<Dish> getAll() {
        return dishRepository.getAll();
    }
}

