package nl.hu.bep3.kitchen.application.response;
import nl.hu.bep3.dish.domain.Dish;
import org.springframework.data.domain.Persistable;
import java.util.List;
import java.util.UUID;

public class OrderResponseDto {

  //public Status status;
  public UUID id;
  public UUID customerId;
  public boolean deliver; // pickup/deliver
  public List<DishOrder> dishOrders;
  public float deliverCosts = 2.50F;
  public String customerMessage;

  public class DishOrder implements Persistable<UUID> {

    private UUID id;
    private int amount;
    private Dish dish;

    public DishOrder(int amount, Dish dish) {
      this.id = UUID.randomUUID();
      this.amount = amount;
      this.dish = dish;
    }

    public Dish getDish() {return dish;
    }

    public float calcPriceDishOrder() {
      // return dish.getPrice * amount;
      // FIXME aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
      return 0.0f;
    }

    public int getAmount() {
      return amount;
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
}

