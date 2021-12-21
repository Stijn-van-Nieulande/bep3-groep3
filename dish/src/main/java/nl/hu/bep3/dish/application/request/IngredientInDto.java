package nl.hu.bep3.dish.application.request;

import java.util.List;
import nl.hu.bep3.dish.domain.FoodAllergy;

public class IngredientInDto {
  public String name;
  public List<FoodAllergy> allergies;
}
