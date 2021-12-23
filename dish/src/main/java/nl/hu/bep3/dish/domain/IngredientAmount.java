package nl.hu.bep3.dish.domain;

import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    IngredientAmount that = (IngredientAmount) o;
    return Float.compare(that.amount, amount) == 0 && id.equals(that.id)
        && amountUnit == that.amountUnit && Objects.equals(ingredient, that.ingredient);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount, amountUnit, ingredient);
  }
}
