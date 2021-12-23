package nl.hu.bep3.order.application.request;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.domain.Status;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;

public class OrderRequestDTO {

//  public Status status;
//  public UUID customerId;
//  public boolean deliver; // pickup/deliver
//  public List<DishOrder> dishOrders;
//  public float deliverCosts = 2.50F;
//  public String customerMessage;
//  public UUID kitchenId;

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
