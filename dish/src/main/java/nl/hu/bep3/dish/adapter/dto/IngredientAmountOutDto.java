package nl.hu.bep3.dish.adapter.dto;

import nl.hu.bep3.dish.domain.IngredientAmount;

public class IngredientAmountOutDto {
  public String ingredientName;
  public double amount;
  public String unit;

  public IngredientAmountOutDto(IngredientAmount ingredientAmount) {
    this.ingredientName = ingredientAmount.getIngredient().getName();
    this.amount = ingredientAmount.getAmount();
    this.unit =
        ingredientAmount.getAmountUnit().toString()
            + ingredientAmount.getAmountUnit().getAbbreviation();
  }
}
