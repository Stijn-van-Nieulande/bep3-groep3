package nl.hu.bep3.kitchen.domain;

import java.util.Objects;
import java.util.UUID;
import nl.hu.bep3.dish.domain.AmountUnit;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProductInStock implements Persistable<UUID> {
  private UUID id;

  private float amount;
  private AmountUnit amountUnit;

  public ProductInStock(UUID id, float amount, AmountUnit unit) {
    this.id = id;
    this.amount = amount;
    this.amountUnit = unit;
  }

  public ProductInStock(){}

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return false;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) { this.amount = amount; }

  public AmountUnit getAmountUnit() {
    return amountUnit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ProductInStock product = (ProductInStock) o;
    return Float.compare(product.amount, amount) == 0 && id.equals(product.id)
        && amountUnit == product.amountUnit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, amount, amountUnit);
  }
}
