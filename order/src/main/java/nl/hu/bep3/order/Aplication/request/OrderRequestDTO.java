package nl.hu.bep3.order.Aplication.request;

import nl.hu.bep3.order.domain.Status;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;

import java.util.List;
import java.util.UUID;

public class OrderRequestDTO {

  public Status status;
  public UUID customerId;
  public boolean deliver; //pickup/deliver
  public List<DishOrder> dishOrders;
  public float deliverCosts = 2.50F;
  public String customerMessage;
}
