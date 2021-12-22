package nl.hu.bep3.order.domain;

import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.Aplication.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;

import java.util.Date;
import java.util.List;

public class Order {

  private Long orderId;
  private Date orderDate;
  private Status status;
  private Customer customer;
  private Payment payment;
  private boolean deliver; //pickup/deliver
  private String paymentMethod;
  private List<DishOrder> dishOrders;
  private float deliverCosts = 2.50F;
  private float minAmountNoDeliverCosts = 25;
  private String customerMessage;
  private Review review;


  public Order(Customer customer, Payment payment, boolean deliver,
      String paymentMethod, List<DishOrder> dishOrders, String message) {
    this.customer = customer;
    this.payment = payment;
    this.deliver = deliver;
    this.customerMessage = message;
    this.paymentMethod = paymentMethod;
    this.dishOrders = dishOrders;
    this.status = Status.PENDING;
  }

  //customer roept dit aan
//  public void placeOrder(List<DishOrder> dishOrders, boolean deliver, String message) {
//    this.dishOrders = dishOrders;
//    this.deliver = deliver;
//    this.customerMessage = message;
//    this.status = Status.PENDING;
//  }

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

  public ReviewResponseDTO setReview(String message, int rating) {
    ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO(rating, message);
    this.review = new Review(message, rating);
    return reviewResponseDTO;
  }

  public Status getStatus() {
    return status;
  }
}
