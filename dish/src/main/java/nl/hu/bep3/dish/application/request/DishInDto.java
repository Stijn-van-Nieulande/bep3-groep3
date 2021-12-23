package nl.hu.bep3.dish.application.request;

import java.util.ArrayList;
import java.util.List;

public class DishInDto {

  public String name;
  public double price;
  public List<IngredientAmountInDto> ingredients = new ArrayList<>();
}
