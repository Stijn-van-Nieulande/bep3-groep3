package nl.hu.bep3.order.domain.valueobjects;

import nl.hu.bep3.order.infrastructure.repository.Persistable;
import nl.hu.bep3.dish.domain.Dish;
import java.util.UUID;


public class DishOrder implements Persistable<UUID> {

  private UUID id;
  private int amount;
  private Dish dish;

  public DishOrder(int amount, Dish dish) {
    this.amount = amount;
    this.dish = dish;
  }

  public float calcPriceDishOrder() {
    //return dish.getPrice * amount;
    //FIXME aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    return 0.0f;
  }

  @Override
  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }
}
