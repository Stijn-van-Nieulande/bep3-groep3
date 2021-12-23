package nl.hu.bep3.order.Aplication.response;

import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.Review;
import nl.hu.bep3.order.domain.Status;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;

import java.util.List;
import java.util.UUID;

public class OrderResponseDTO {

  public UUID id;
  public Status status;
  public Customer customer;
  public boolean deliver; //pickup/deliver
  public List<DishOrder> dishOrders;
  public float deliverCosts = 2.50F;
  public String customerMessage;
  public Review review;

  public OrderResponseDTO(Order order) {
    this.id = order.getId();
    this.status = order.getStatus();
    this.customer = order.getCustomer();
    this.deliver = order.isDeliver();
    this.dishOrders = order.getDishOrders();
    this.deliverCosts = order.getDeliverCosts();
    this.customerMessage = order.getCustomerMessage();
    this.review = order.getReview();
  }
}
