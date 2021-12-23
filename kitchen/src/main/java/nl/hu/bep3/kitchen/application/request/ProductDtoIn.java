package nl.hu.bep3.kitchen.application.request;

import java.util.List;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.dish.domain.FoodAllergy;

public class ProductDtoIn {
  public String name;
  public List<FoodAllergy> allergies;

  public float amount;
  public AmountUnit amountUnit;
}
