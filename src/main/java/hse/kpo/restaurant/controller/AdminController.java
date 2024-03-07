package hse.kpo.restaurant.controller;

import hse.kpo.restaurant.model.Dish;
import hse.kpo.restaurant.service.AdminService;
import hse.kpo.restaurant.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;
    private DishService dishService;

    @PostMapping("menu/add")
    public ResponseEntity<String> addDishItem(@RequestBody Dish dish, @RequestParam String login) {
        boolean isAdmin = adminService.ifAdminExistOrNot(login);
        if (!isAdmin) {
            return ResponseEntity.badRequest().body("ERROR: Unauthorized access");
        }
        String addDishResponse = dishService.addDishItem(dish);
        if (addDishResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(addDishResponse);
        }
        return ResponseEntity.ok(addDishResponse);
    }

    @PostMapping("menu/delete")
    public ResponseEntity<String> deleteDishItem(@RequestParam String dishId, @RequestParam String login) {
        boolean isAdmin = adminService.ifAdminExistOrNot(login);
        if (!isAdmin) {
            return ResponseEntity.badRequest().body("ERROR: Unauthorized access");
        }
        Integer intId = null;
        try {
            intId = Integer.parseInt(dishId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid param value");
        }
        Dish toDelete = dishService.getById(intId);
        String deleteDishResponse = dishService.removeDishItem(toDelete);
        if (deleteDishResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(deleteDishResponse);
        }
        return ResponseEntity.ok(deleteDishResponse);
    }

    @PostMapping("menu/update")
    public ResponseEntity<String> updateDishItem(@RequestParam String dishId,
                                              @RequestParam(required = false) String dishDurationInMin,
                                              @RequestParam(required = false) String dishPrice,
                                              @RequestParam String login) {
        boolean isAdmin = adminService.ifAdminExistOrNot(login);
        if (!isAdmin) {
            return ResponseEntity.badRequest().body("ERROR: Unauthorized access");
        }
        Integer intId = null;
        try {
            intId = Integer.parseInt(dishId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid param value");
        }
        Dish toUpdate = dishService.getById(intId);
        Integer intDuration = null;
        Integer intPrice = null;
        if (dishDurationInMin != null) {
            try {
                intDuration = Integer.parseInt(dishDurationInMin);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("ERROR: Invalid param value");
            }
        }
        if (dishPrice != null) {
            try {
                intPrice = Integer.parseInt(dishPrice);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid param value");
            }
        }

        String updateDishResponse = dishService.updateDishItem(toUpdate, intDuration, intPrice);
        if (updateDishResponse.contains("ERROR")) {
            return ResponseEntity.badRequest().body(updateDishResponse);
        }
        return ResponseEntity.ok(updateDishResponse);
    }

    @GetMapping("admin/menu")
    public List<Dish> getAdminAllDishItems()
    {
        return dishService.getAll();
    }
}
