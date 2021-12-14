package nl.hu.bep3.dish.adapter.dto;

import nl.hu.bep3.dish.domain.FoodAllergy;
import nl.hu.bep3.dish.domain.Ingredient;

import java.util.List;

public class IngredientDto {
  public Long id;
  public String name;
  public List<FoodAllergy> allergies;

  public IngredientDto(Ingredient ingredient) {
    this.id = ingredient.getId();
    this.name = ingredient.getName();
    this.allergies = ingredient.getAllergies();
  }
}
