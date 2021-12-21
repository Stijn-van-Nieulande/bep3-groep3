package nl.hu.bep3.dish.domain;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IngredientAmount {
  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private float amount;
  private AmountUnit amountUnit;

//  @ManyToOne
//  @JoinColumn(name = "ingredient_id")
  private Ingredient ingredient;

  public IngredientAmount() {
  }

  public IngredientAmount(float amount, AmountUnit amountUnit, Ingredient ingredient) {
    this.id = UUID.randomUUID();
    this.amount = amount;
    this.amountUnit = amountUnit;
    this.ingredient = ingredient;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public AmountUnit getAmountUnit() {
    return amountUnit;
  }

  public void setAmountUnit(AmountUnit amountUnit) {
    this.amountUnit = amountUnit;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }
}
