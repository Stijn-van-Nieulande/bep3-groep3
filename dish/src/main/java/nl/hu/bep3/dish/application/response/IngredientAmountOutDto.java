package nl.hu.bep3.dish.application.response;

import nl.hu.bep3.dish.domain.FoodAllergy;
import nl.hu.bep3.dish.domain.IngredientAmount;

import java.util.List;

public class IngredientAmountOutDto {
  public String ingredientName;
  public List<FoodAllergy> allergies;
  public double amount;
  public String unit;

  public IngredientAmountOutDto(IngredientAmount ingredientAmount) {
    this.ingredientName = ingredientAmount.getIngredient().getName();
    this.allergies = ingredientAmount.getIngredient().getAllergies();
    this.amount = ingredientAmount.getAmount();
    this.unit =
        ingredientAmount.getAmountUnit().toString()
            + ingredientAmount.getAmountUnit().getAbbreviation();
  }
}
