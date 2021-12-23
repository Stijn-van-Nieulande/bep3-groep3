package nl.hu.bep3.dish.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Persistable;

public class IngredientAmount implements Persistable<UUID> {

  private UUID id;
  private float amount;
  private AmountUnit amountUnit;
  private Ingredient ingredient;

  public IngredientAmount() {}

  public IngredientAmount(
      final float amount, final AmountUnit amountUnit, final Ingredient ingredient) {
    this.id = UUID.randomUUID();
    this.amount = amount;
    this.amountUnit = amountUnit;
    this.ingredient = ingredient;
  }

  @Override
  public UUID getId() {
    return this.id;
  }

  public float getAmount() {
    return this.amount;
  }

  public void setAmount(final float amount) {
    this.amount = amount;
  }

  public AmountUnit getAmountUnit() {
    return this.amountUnit;
  }

  public void setAmountUnit(final AmountUnit amountUnit) {
    this.amountUnit = amountUnit;
  }

  public Ingredient getIngredient() {
    return this.ingredient;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final IngredientAmount that = (IngredientAmount) o;
    return Float.compare(that.amount, this.amount) == 0
        && this.id.equals(that.id)
        && this.amountUnit == that.amountUnit
        && Objects.equals(this.ingredient, that.ingredient);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.amount, this.amountUnit, this.ingredient);
  }
}
