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


    public void CreatePaymentOrder(){
        Payment newPayment = new Payment(false, calcTotPrice());
    }

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

    //customer roept dit aan
    public void placeOrder(List<DishOrder> dishOrders, boolean deliver, String message){
        this.dishOrders = dishOrders;
        this.deliver = deliver;
        this.customerMessage = message;

        this.status = Status.ACCEPTED;
    }
    

    public void setStatusPrepairing(){
        status = Status.PREPAIRING;
    }

    //keuken kan deze aanroepen als de order bereid is
    public void orderIsPrepaired(){
        if (deliver){
            status = Status.DELIVERING;
        }
        else {
            status = Status.READYFORPICKUP;

        }
    }

    public void setStatusDelivering(){
        status = Status.DELIVERING;
    }

    public void setStatusReadyForPickup(){
        status = Status.READYFORPICKUP;
    }

    public void setStatusFinished(){
        status = Status.FINISHED;
    }

    public void setStatusCanceled(){
        status = Status.CANCELED;
    }


    public void setReview(String message, int rating){
        this.review = new Review(message, rating);
    }

    public void setOrderInHistory(){
        customer.addOrderToHistory(this);
    }




}
