package nl.hu.bep3.order.Domain;

import nl.hu.bep3.order.Domain.ValueObjects.DishOrder;

import java.util.Date;
import java.util.List;

public class Order {
    private Long orderId;
    private Date orderDate;
    private String adres;
    private Status status;
    private Customer customer;
    private Payment payment;
    private boolean deliver; //pickup/deliver
    private String paymentMethod;
    private List<DishOrder> dishOrders;
    private float deliverCosts = 2.50F;
    private String customerMessage;
    private Review review;


    public Order(String adres, Customer customer, Payment payment, boolean deliver, String paymentMethod, List<DishOrder> dishOrders, String customerMessage) {
        this.adres = adres;
        this.customer = customer;
        this.payment = payment;
        this.deliver = deliver;
        this.paymentMethod = paymentMethod;
        this.dishOrders = dishOrders;
        this.customerMessage = customerMessage;
    }



    //customer roept dit aan
    public void placeOrder(List<DishOrder> dishOrders, boolean deliver, String message){
        this.dishOrders = dishOrders;
        this.deliver = deliver;
        this.customerMessage = message;

        this.status = Status.ACCEPTED;
    }

//    public void CreatePaymentOrder(){
//        Payment newPayment = new Payment(false, calcTotPrice());
//    }

    public float calcTotPrice(){
        float totPrice = 0;
        for (DishOrder dishOrder : dishOrders){
            totPrice = dishOrder.calcPriceDishOrder();
        }
        if (deliver == true){
            totPrice =+ deliverCosts;
        }
        return totPrice;
    }

    public void setStatus(String status){
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

    public void setReview(String message, int rating){
        this.review = new Review(message, rating);
    }
}
