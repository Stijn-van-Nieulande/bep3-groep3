package nl.hu.bep3.order.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.domain.exception.StatusInvalidException;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class Order implements Persistable<UUID> {

  private UUID id;
  private LocalDate orderDate;
  private Status status;
  private Customer customer;
  private boolean deliver; //pickup/deliver
  private List<DishOrder> dishOrders;
  private double deliverCosts = 2.50;
  private double minAmountNoDeliverCosts = 25;
  private String customerMessage;
  private Review review;
  private UUID kitchenId;

  public Order(final Customer customer, final boolean deliver, final List<DishOrder> dishOrders,
      final String customerMessage, final UUID kitchenId) {
    this.customer = customer;
    this.deliver = deliver;
    this.customerMessage = customerMessage;
    this.dishOrders = dishOrders;
    this.status = Status.PENDING;
    this.kitchenId = kitchenId;
    this.orderDate = LocalDate.now();
  }

  public double calcTotPrice() {
    double totPrice = 0;
    for (final DishOrder dishOrder : this.dishOrders) {
      totPrice = dishOrder.calcPriceDishOrder();
    }
    if (this.deliver || totPrice < this.minAmountNoDeliverCosts) {
      totPrice = +this.deliverCosts;
    }
    return totPrice;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(final String status) {
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

  public void setStatus(final Status status) {
    this.status = status;
  }

  public UUID getId() {
    return this.id;
  }

  @Override
  public void setId(final UUID id) {
    this.id = id;
  }

  public LocalDate getOrderDate() {
    return this.orderDate;
  }

  public void setOrderDate(final LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }

  public boolean isDeliver() {
    return this.deliver;
  }

  public void setDeliver(final boolean deliver) {
    this.deliver = deliver;
  }

  public List<DishOrder> getDishOrders() {
    return this.dishOrders;
  }

  public void setDishOrders(final List<DishOrder> dishOrders) {
    this.dishOrders = dishOrders;
  }

  public double getDeliverCosts() {
    return this.deliverCosts;
  }

  public void setDeliverCosts(final float deliverCosts) {
    this.deliverCosts = deliverCosts;
  }

  public double getMinAmountNoDeliverCosts() {
    return this.minAmountNoDeliverCosts;
  }

  public void setMinAmountNoDeliverCosts(final float minAmountNoDeliverCosts) {
    this.minAmountNoDeliverCosts = minAmountNoDeliverCosts;
  }

  public String getCustomerMessage() {
    return this.customerMessage;
  }

  public void setCustomerMessage(final String customerMessage) {
    this.customerMessage = customerMessage;
  }

  public Review getReview() {
    return this.review;
  }

  public void setReview(final Review review) {
    this.review = review;
  }

  public UUID getKitchenId() {
    return this.kitchenId;
  }

  public void setKitchenId(final UUID kitchenId) {
    this.kitchenId = kitchenId;
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }
}
