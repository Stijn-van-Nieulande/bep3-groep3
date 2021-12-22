package nl.hu.bep3.dish.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.dish.domain.Dish;

public interface DishRepository {

  Optional<Dish> findById(UUID id);

  List<Dish> findAll();

  Dish save(Dish dish);

  void delete(Dish dish);
}
