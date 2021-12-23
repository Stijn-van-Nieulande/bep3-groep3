package nl.hu.bep3.order.domain.valueobjects;

import java.util.UUID;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class DishOrder implements Persistable<UUID> {

  private final int amount;
  private final UUID dishId;
  private final double price;
  private UUID id;

  public DishOrder(final int amount, final UUID dishId, final double price) {
    this.amount = amount;
    this.dishId = dishId;
    this.price = price;
  }

  public double calcPriceDishOrder() {
    return this.price * this.amount;
  }

  public UUID getDish() {
    return this.dishId;
  }

  public int getAmount() {
    return this.amount;
  }

  @Override
  public UUID getId() {
    return this.id;
  }

  @Override
  public void setId(final UUID id) {
    this.id = id;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }
}
