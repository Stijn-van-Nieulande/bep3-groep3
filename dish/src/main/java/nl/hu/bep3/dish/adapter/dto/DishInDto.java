package nl.hu.bep3.dish.adapter.dto;

import java.util.ArrayList;
import java.util.List;

public class DishInDto {
  public String name;
  public double price;
  public List<IngredientAmountOutDto> ingredients = new ArrayList<IngredientAmountOutDto>();
}
