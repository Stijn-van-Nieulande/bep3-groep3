package nl.hu.bep3.order.application.response;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;

public class OrderResponseToKitchenDto {
  public UUID id;
  public List<DishOrder> dishOrders;
  public UUID kitchenId;

  public OrderResponseToKitchenDto(final Order order) {
    this.id = order.getId();
    this.dishOrders = order.getDishOrders();
    this.kitchenId = order.getKitchenId();
  }
}
