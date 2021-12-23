package nl.hu.bep3.dish.application.response;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.domain.FoodAllergy;
import nl.hu.bep3.dish.domain.IngredientAmount;

public class IngredientAmountOutDto {

  public UUID id;
  public String ingredientName;
  public List<FoodAllergy> allergies;
  public double amount;
  public String unit;

  public IngredientAmountOutDto(final IngredientAmount ingredientAmount) {
    this.id = ingredientAmount.getIngredient().getId();
    this.ingredientName = ingredientAmount.getIngredient().getName();
    this.allergies = ingredientAmount.getIngredient().getAllergies();
    this.amount = ingredientAmount.getAmount();
    this.unit = ingredientAmount.getAmountUnit().toString();
  }

  public IngredientAmountOutDto() {}
}
