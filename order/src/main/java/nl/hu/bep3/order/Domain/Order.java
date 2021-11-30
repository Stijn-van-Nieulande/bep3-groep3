package nl.hu.bep3.order.Domain;

import java.util.Date;

public class Order {
    private Long orderId;
    private Date orderDate;
    private String adres;
    private Status status;
    private Customer customer;
    private Payment payment;
    private float totCosts;
    private boolean deliver; //pickup/deliver
    private String paymentMethod;
}
