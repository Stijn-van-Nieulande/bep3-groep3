package nl.hu.bep3.dish.application.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.IngredientAmount;

public class DishOutDto {

  public UUID id;
  public String name;
  public double price;
  public List<IngredientAmountOutDto> ingredients = new ArrayList<IngredientAmountOutDto>();

  public DishOutDto(Dish dish) {
    this.id = dish.getId();
    this.name = dish.getName();
    this.price = dish.getPrice();
    if (dish.getIngredients() != null) {
      for (IngredientAmount ingredientAmount : dish.getIngredients()) {
        ingredients.add(new IngredientAmountOutDto(ingredientAmount));
      }
    }
  }

  public DishOutDto() {
  }
}
