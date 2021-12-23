package nl.hu.bep3.order.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.application.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.exception.StatusInvalidException;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class Order implements Persistable<UUID> {

  private UUID id;
  private Date orderDate;
  private Status status;
  private Customer customer;
  private boolean deliver; //pickup/deliver
  private List<DishOrder> dishOrders;
  private double deliverCosts = 2.50;
  private double minAmountNoDeliverCosts = 25;
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

  public double calcTotPrice() {
    double totPrice = 0;
    for (DishOrder dishOrder : dishOrders) {
      totPrice = dishOrder.calcPriceDishOrder();
    }
    if (deliver == true || totPrice < minAmountNoDeliverCosts) {
      totPrice = +deliverCosts;
    }
    return totPrice;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(String status) {
    System.out.println(status.toLowerCase());
    switch (status.toLowerCase()) {
      case "accepted" -> this.status = Status.ACCEPTED;
      case "prepairing" -> this.status = Status.PREPAIRING;
      case "prepaired" -> this.status = Status.PREPAIRED;
      case "ready for pickup" -> this.status = Status.READYFORPICKUP;
      case "delivering" -> this.status = Status.DELIVERING;
      case "finished" -> this.status = Status.FINISHED;
      case "cancelled" -> this.status = Status.CANCELLED;
      case "pending" -> this.status = Status.PENDING;
      default -> throw new StatusInvalidException(status);
    }
  }

//  public Status getStatus() {
//    return this.status;
//  }

//  public void setStatus(String status) {
//    System.out.println(status);
//    status.toUpperCase();
//    status.replaceAll("\\s+","");
//    System.out.println(status);
//    this.status = Status.valueOf(status);
//  }

  public void setStatus(Status status) {
    this.status = status;
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

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public boolean isDeliver() {
    return deliver;
  }

  public void setDeliver(boolean deliver) {
    this.deliver = deliver;
  }

  public List<DishOrder> getDishOrders() {
    return dishOrders;
  }

  public void setDishOrders(List<DishOrder> dishOrders) {
    this.dishOrders = dishOrders;
  }

  public double getDeliverCosts() {
    return deliverCosts;
  }

  public void setDeliverCosts(float deliverCosts) {
    this.deliverCosts = deliverCosts;
  }

  public double getMinAmountNoDeliverCosts() {
    return minAmountNoDeliverCosts;
  }

  public void setMinAmountNoDeliverCosts(float minAmountNoDeliverCosts) {
    this.minAmountNoDeliverCosts = minAmountNoDeliverCosts;
  }

  public String getCustomerMessage() {
    return customerMessage;
  }

  public void setCustomerMessage(String customerMessage) {
    this.customerMessage = customerMessage;
  }

  public Review getReview() {
    return review;
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
