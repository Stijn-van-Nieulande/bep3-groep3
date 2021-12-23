package nl.hu.bep3.dish.application.request;

import nl.hu.bep3.dish.domain.AmountUnit;
import java.util.UUID;

public class IngredientAmountInDto {

  public UUID ingredientId;
  public float amount;
  public AmountUnit amountUnit;
}
