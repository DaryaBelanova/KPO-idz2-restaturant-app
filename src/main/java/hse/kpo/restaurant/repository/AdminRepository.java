package hse.kpo.restaurant.repository;

import hse.kpo.restaurant.model.Admin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AdminRepository {
    private List<Admin> admins = new ArrayList<>(Arrays.asList(
            new Admin(1, "admin1", "wasdf"),
            new Admin(2, "admin2", "qwerty")
    ));
    public Admin findFirstByLogin(String login) {
        return admins.stream().filter(admin -> admin.getLogin().equals(login)).findFirst().orElse(null);
    }
}
