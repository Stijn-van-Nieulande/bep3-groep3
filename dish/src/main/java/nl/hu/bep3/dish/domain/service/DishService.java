package nl.hu.bep3.dish.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.domain.Dish;

public interface DishService {

  Dish getDishById(UUID id);

  Dish createDish(DishInDto dishInDto);

  Dish updateDish(UUID id, DishInDto dishInDto);

  void deleteDish(UUID id);
}
