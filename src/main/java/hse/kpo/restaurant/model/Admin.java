package hse.kpo.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Admin {
    private Integer id;
    private String login;
    private String password;
}
