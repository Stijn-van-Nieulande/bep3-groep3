package nl.hu.bep3.kitchen.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Storage {
  private Kitchen kitchen;
  private int capacity;
  private List<ProductInStock> productInStock= new ArrayList<>();

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

  public UUID getId() {
    return null;
  }

  public boolean isNew() {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Storage storage = (Storage) o;
    return capacity == storage.capacity && Objects.equals(kitchen, storage.kitchen)
        && Objects.equals(productInStock, storage.productInStock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kitchen, capacity, productInStock);
  }
}
