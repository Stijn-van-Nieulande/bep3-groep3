package nl.hu.bep3.dish.application.request;

import nl.hu.bep3.dish.domain.FoodAllergy;
import nl.hu.bep3.dish.domain.Ingredient;

import java.util.List;
import java.util.UUID;

public class IngredientInDto {
  public String name;
  public List<FoodAllergy> allergies;
}
