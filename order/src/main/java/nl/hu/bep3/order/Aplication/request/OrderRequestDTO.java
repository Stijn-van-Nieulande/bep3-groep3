package nl.hu.bep3.order.Aplication.request;

import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.domain.Payment;
import nl.hu.bep3.order.domain.Review;
import nl.hu.bep3.order.domain.Status;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;

import java.util.List;

public class OrderRequestDTO {

  private Status status;
  private Customer customer;
  private Payment payment;
  private boolean deliver; //pickup/deliver
  private String paymentMethod;
  private List<DishOrder> dishOrders;
  private float deliverCosts = 2.50F;
  private String customerMessage;
  private Review review;
  private String message;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public boolean isDeliver() {
    return deliver;
  }

  public void setDeliver(boolean deliver) {
    this.deliver = deliver;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public List<DishOrder> getDishOrders() {
    return dishOrders;
  }

  public void setDishOrders(List<DishOrder> dishOrders) {
    this.dishOrders = dishOrders;
  }

  public float getDeliverCosts() {
    return deliverCosts;
  }

  public void setDeliverCosts(float deliverCosts) {
    this.deliverCosts = deliverCosts;
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

  public void setReview(Review review) {
    this.review = review;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
