package nl.hu.bep3.dish.adapter.dto;

import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.IngredientAmount;

import java.util.ArrayList;
import java.util.List;

public class DishOutDto {
  public Long id;
  public String name;
  public double price;
  public List<IngredientAmountOutDto> ingredients = new ArrayList<IngredientAmountOutDto>();

  public DishOutDto(Dish dish) {
    this.id = dish.getId();
    this.name = dish.getName();
    this.price = dish.getPrice();
    for (IngredientAmount ingredientAmount : dish.getIngredients()) {
      ingredients.add(new IngredientAmountOutDto(ingredientAmount));
    }
  }
}
