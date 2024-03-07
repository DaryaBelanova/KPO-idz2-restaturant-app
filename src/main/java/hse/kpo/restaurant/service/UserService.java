package hse.kpo.restaurant.service;

import hse.kpo.restaurant.model.User;
import hse.kpo.restaurant.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public String signIn(String login) {
        User found = userRepository.findFirstByLogin(login);
        if (found == null) {
            return "ERROR: User not found";
        }
        return "Sign in successfully";
    }

    public String authenticate(String login) {
        if (userRepository.findFirstByLogin(login) == null) {
            return "ERROR: User not found";
        }
        return "Authenticated successfully";
    }

    public User getByLogin(String login) {
        return userRepository.findFirstByLogin(login);
    }
}
