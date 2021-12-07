package nl.hu.bep3.dish.application;

import nl.hu.bep3.dish.adapter.DishRepository;
import nl.hu.bep3.dish.adapter.IngredientRepository;
import nl.hu.bep3.dish.adapter.dto.DishOutDto;
import nl.hu.bep3.dish.adapter.dto.IngredientDto;
import nl.hu.bep3.dish.application.Exceptions.DishNotFoundException;
import nl.hu.bep3.dish.application.Exceptions.IngredientNotFoundException;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import org.springframework.stereotype.Service;

@Service
public class DishApplicationService {
  private final DishRepository dishRepository;
  private final IngredientRepository ingredientRepository;

  public DishApplicationService(
      DishRepository dishRepository, IngredientRepository ingredientRepository) {
    this.dishRepository = dishRepository;
    this.ingredientRepository = ingredientRepository;
  }

  public DishOutDto getDishById(Long id) {
    Dish dish =
        dishRepository
            .findById(id)
            .orElseThrow(() -> new DishNotFoundException("No dish found with id: " + id));
    return new DishOutDto(dish);
  }

  public IngredientDto getIngredientById(Long id) {
    final Ingredient ingredient =
        ingredientRepository
            .findById(id)
            .orElseThrow(
                    () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    return new IngredientDto(ingredient);
  }
}
