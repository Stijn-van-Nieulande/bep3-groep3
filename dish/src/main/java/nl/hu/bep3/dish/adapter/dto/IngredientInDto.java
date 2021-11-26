package nl.hu.bep3.dish.adapter.dto;

import nl.hu.bep3.dish.domain.FoodAllergy;

import java.util.List;

public class IngredientInDto {
  public Long id;
  public String name;
  public List<FoodAllergy> allergies;
}
