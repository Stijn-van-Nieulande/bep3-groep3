package nl.hu.bep3.order.application.request;

import java.util.List;
import java.util.UUID;

public class OrderRequestDto {

  public UUID customerId;
  public boolean deliver;
  public double deliverCost = 2.50;

  public List<DishOrderDto> dishOrders;

  public String customerMessage;

  public UUID kitchenId;

  public static class DishOrderDto {
    public int amountOfDish;
    public UUID dishId;
  }
}
