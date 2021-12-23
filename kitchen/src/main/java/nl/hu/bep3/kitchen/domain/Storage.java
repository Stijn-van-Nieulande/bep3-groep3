package nl.hu.bep3.kitchen.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Storage {

  private Kitchen kitchen;
  private int capacity;
  private List<ProductInStock> productInStock = new ArrayList<>();

  public Storage() {}

  public Storage(final int capacity) {
    this.capacity = capacity;
  }

  public int getCapacity() {
    return this.capacity;
  }

  public void setCapacity(final int capacity) {
    this.capacity = capacity;
  }

  public List<ProductInStock> getIngredientInStock() {
    return this.productInStock;
  }

  public UUID getId() {
    return null;
  }

  public boolean isNew() {
    return false;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final Storage storage = (Storage) o;
    return this.capacity == storage.capacity
        && Objects.equals(this.kitchen, storage.kitchen)
        && Objects.equals(this.productInStock, storage.productInStock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.kitchen, this.capacity, this.productInStock);
  }
}
