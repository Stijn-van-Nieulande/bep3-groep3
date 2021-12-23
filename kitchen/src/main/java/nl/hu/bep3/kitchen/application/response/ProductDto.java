package nl.hu.bep3.kitchen.application.response;

import java.util.ArrayList;
import java.util.List;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.kitchen.domain.FoodAllergy;

public class ProductDto {

  public float amount;
  public String ingredientName;
  public List<FoodAllergy> allergies = new ArrayList<>();
  public AmountUnit amountUnit;
}
