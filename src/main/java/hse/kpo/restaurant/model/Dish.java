package hse.kpo.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Dish {
    private final Integer id;
    private final String description;
    @Setter
    private Integer durationInMin;
    @Setter
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(description, dish.description) && Objects.equals(durationInMin, dish.durationInMin) && Objects.equals(price, dish.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, durationInMin, price);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", durationInMin=" + durationInMin +
                ", price=" + price +
                '}';
    }
}
