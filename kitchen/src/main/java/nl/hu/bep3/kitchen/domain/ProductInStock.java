package nl.hu.bep3.kitchen.domain;

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
}
