package nl.hu.bep3.dish.domain.repository;

import nl.hu.bep3.dish.domain.Dish;

import java.util.Optional;
import java.util.UUID;

public interface DishRepository {
    Optional<Dish> findById(UUID id);

    Dish save(Dish dish);
}
