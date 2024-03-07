package hse.kpo.restaurant.repository;

import hse.kpo.restaurant.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "user1"),
            new User(2, "user2"),
            new User(3, "user3")
    ));

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public User findFirstByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
    }
}
