package nl.hu.bep3.dish.domain.service;

import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;

public interface DishApplicationService {
    DishOutDto getDishById(Long id);
    IngredientOutDto getIngredientById(Long id);

    DishOutDto createDish(DishInDto dishInDto);

    IngredientOutDto createIngredient(IngredientInDto ingredientInDto);
}
