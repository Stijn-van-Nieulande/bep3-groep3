package nl.hu.bep3.order.domain.valueobjects;

import java.util.UUID;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class DishOrder implements Persistable<UUID> {

  private UUID id;
  private int amount;
  private UUID dishId;
  private double price;

  public DishOrder(int amount, UUID dishId, double price) {
    this.amount = amount;
    this.dishId = dishId;
    this.price = price;
  }

  public double calcPriceDishOrder() {
    return price * amount;
  }

  public UUID getDish(){
    return dishId;
  }

  public int getAmount(){
    return amount;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }
}
