package nl.hu.bep3.order.Domain;

import nl.hu.bep3.order.Domain.ValueObjects.OrderId;
import nl.hu.bep3.order.Domain.ValueObjects.PaymentId;

import java.util.Date;

public class Order {
    private OrderId orderId;
    private Date orderDate;
    private String adres;
    private Status status;
    private CustomerId customerId;
    private PaymentId paymentId;
    private float totCosts;
    private boolean deliver; //pickup/deliver
    private String paymentMethod;
}
