package nl.hu.bep3.kitchen.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Storage {

  //TODO: check if this works
  @Id
  private UUID id;

  private Kitchen kitchen;

  private int capacity;

  private List<ProductInStock> productInStock;

  public Storage() {
  }

  public Storage(int capacity) {
    this.capacity = capacity;
  }

  public int getCapacity() {
    return capacity;
  }

  public List<ProductInStock> getIngredientInStock() {
    return productInStock;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
