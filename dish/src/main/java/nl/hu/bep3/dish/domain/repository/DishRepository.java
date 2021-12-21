package nl.hu.bep3.dish.domain.repository;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.dish.domain.Dish;

public interface DishRepository {
    Optional<Dish> findById(UUID id);

    Dish save(Dish dish);
}
