package nl.hu.bep3.dish.application.request;

import java.util.UUID;
import nl.hu.bep3.dish.domain.AmountUnit;

public class IngredientAmountInDto {

  public UUID ingredientId;
  public float amount;
  public AmountUnit amountUnit;
}
