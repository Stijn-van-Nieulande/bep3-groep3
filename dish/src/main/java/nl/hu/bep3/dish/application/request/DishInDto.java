package nl.hu.bep3.dish.application.request;

import nl.hu.bep3.dish.application.response.IngredientAmountOutDto;

import java.util.ArrayList;
import java.util.List;

public class DishInDto {
  public String name;
  public double price;
  public List<IngredientAmountInDto> ingredients = new ArrayList<IngredientAmountInDto>();
}
