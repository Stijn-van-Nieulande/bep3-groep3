package nl.hu.bep3.order.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.application.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class Order implements Persistable<UUID> {

  private UUID id;
  private Date orderDate;
  private Status status;
  private Customer customer;
  private boolean deliver; //pickup/deliver
  private List<DishOrder> dishOrders;
  private float deliverCosts = 2.50F;
  private float minAmountNoDeliverCosts = 25;
  private String customerMessage;
  private Review review;
  private UUID kitchenId;

  public Order(Customer customer, boolean deliver, List<DishOrder> dishOrders,
      String customerMessage, UUID kitchenId) {
    this.customer = customer;
    this.deliver = deliver;
    this.customerMessage = customerMessage;
    this.dishOrders = dishOrders;
    this.status = Status.PENDING;
    this.kitchenId = kitchenId;
  }

  public float calcTotPrice() {
    float totPrice = 0;
    for (DishOrder dishOrder : dishOrders) {
      totPrice = dishOrder.calcPriceDishOrder();
    }
    if (deliver == true || totPrice < minAmountNoDeliverCosts) {
      totPrice = +deliverCosts;
    }
    return totPrice;
  }

  public ReviewResponseDTO setReview(String message, int rating) {
    ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO(rating, message);
    this.review = new Review(message, rating);
    return reviewResponseDTO;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(String status) {
    switch (status) {
      case "accepted" -> this.status = Status.ACCEPTED;
      case "prepairing" -> this.status = Status.PREPAIRING;
      case "prepaired" -> this.status = Status.PREPAIRED;
      case "ready for pickup" -> this.status = Status.READYFORPICKUP;
      case "delivering" -> this.status = Status.DELIVERING;
      case "finished" -> this.status = Status.FINISHED;
      case "canceled" -> this.status = Status.CANCELED;
      case "pending" -> this.status = Status.PENDING;
      default -> {
      }
    }
  }

  public UUID getId() {
    return id;
  }

  @Override
  public void setId(UUID id) {
    this.id = id;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public Customer getCustomer() {
    return customer;
  }

  public boolean isDeliver() {
    return deliver;
  }

  public List<DishOrder> getDishOrders() {
    return dishOrders;
  }

  public float getDeliverCosts() {
    return deliverCosts;
  }

  public float getMinAmountNoDeliverCosts() {
    return minAmountNoDeliverCosts;
  }

  public String getCustomerMessage() {
    return customerMessage;
  }

  public Review getReview() {
    return review;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setDeliver(boolean deliver) {
    this.deliver = deliver;
  }

  public void setDishOrders(List<DishOrder> dishOrders) {
    this.dishOrders = dishOrders;
  }

  public void setDeliverCosts(float deliverCosts) {
    this.deliverCosts = deliverCosts;
  }

  public void setMinAmountNoDeliverCosts(float minAmountNoDeliverCosts) {
    this.minAmountNoDeliverCosts = minAmountNoDeliverCosts;
  }

  public void setCustomerMessage(String customerMessage) {
    this.customerMessage = customerMessage;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public UUID getKitchenId() {
    return kitchenId;
  }

  public void setKitchenId(UUID kitchenId) {
    this.kitchenId = kitchenId;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }
}
