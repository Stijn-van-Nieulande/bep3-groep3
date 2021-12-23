package nl.hu.bep3.order.domain.valueobjects;

import java.util.UUID;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class DishOrder implements Persistable<UUID> {

  private UUID id;
  private int amount;
  private Dish dish;

  public DishOrder(int amount, Dish dish) {
    this.amount = amount;
    this.dish = dish;
  }

  public float calcPriceDishOrder() {
    // return dish.getPrice * amount;
    // FIXME aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    return 0.0f;
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
