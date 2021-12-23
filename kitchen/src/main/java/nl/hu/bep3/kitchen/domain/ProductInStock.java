package nl.hu.bep3.kitchen.domain;

import java.util.Objects;
import java.util.UUID;
import nl.hu.bep3.dish.domain.AmountUnit;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

public class ProductInStock implements Persistable<UUID> {

  private UUID id;

  private float amount;
  private AmountUnit amountUnit;

  public ProductInStock(final UUID id, final float amount, final AmountUnit unit) {
    this.id = id;
    this.amount = amount;
    this.amountUnit = unit;
  }

  public ProductInStock() {}

  @Override
  public UUID getId() {
    return this.id;
  }

  @Override
  public boolean isNew() {
    return false;
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final ProductInStock product = (ProductInStock) o;
    return Float.compare(product.amount, this.amount) == 0
        && this.id.equals(product.id)
        && this.amountUnit == product.amountUnit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.amount, this.amountUnit);
  }
}
