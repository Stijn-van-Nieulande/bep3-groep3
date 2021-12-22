package nl.hu.bep3.kitchen.application.request;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.dish.domain.FoodAllergy;

public class ProductDtoIn {

  public UUID ingredientId;

  public String name;
  public List<FoodAllergy> allergies;

  public float amount;
  public AmountUnit amountUnit;
}
