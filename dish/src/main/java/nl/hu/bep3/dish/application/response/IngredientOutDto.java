package nl.hu.bep3.dish.application.response;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.domain.FoodAllergy;
import nl.hu.bep3.dish.domain.Ingredient;

public class IngredientOutDto {

  public UUID id;
  public String name;
  public List<FoodAllergy> allergies;

  public IngredientOutDto(final Ingredient ingredient) {
    this.id = ingredient.getId();
    this.name = ingredient.getName();
    this.allergies = ingredient.getAllergies();
  }

  public IngredientOutDto() {}
}
