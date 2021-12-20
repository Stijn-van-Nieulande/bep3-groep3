package nl.hu.bep3.order.Aplication.response;

import nl.hu.bep3.order.Domain.Payment;
import nl.hu.bep3.order.Domain.Review;
import nl.hu.bep3.order.Domain.Status;
import nl.hu.bep3.order.Domain.ValueObjects.DishOrder;

import java.util.List;

public class OrderResponseDTO {
    private Long id;
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
}
