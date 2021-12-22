package nl.hu.bep3.dish.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.domain.Ingredient;

public interface IngredientService {

  Ingredient createIngredient(IngredientInDto ingredientInDto);

  Ingredient getIngredientById(UUID id);

  void deleteIngredient(UUID id);

  List<Ingredient> getAllIngredients();

  Ingredient updateIngredient(UUID id, IngredientInDto ingredientInDto);
}
