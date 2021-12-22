package nl.hu.bep3.kitchen.domain;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProductInStock {

  @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private float amount;
  private UUID ingredientId;
  private AmountUnit amountUnit;

  public ProductInStock(UUID id, float amount) {
    this.id = id;
    this.amount = amount;
  }

  public UUID getId() {
    return id;
  }

  public float getAmount() {
    return amount;
  }

  public UUID getIngredientId() {
    return ingredientId;
  }

  public AmountUnit getAmountUnit() {
    return amountUnit;
  }
}
