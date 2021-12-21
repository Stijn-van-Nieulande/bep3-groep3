package nl.hu.bep3.order.Aplication.response;

import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.Domain.Payment;
import nl.hu.bep3.order.Domain.Review;
import nl.hu.bep3.order.Domain.Status;
import nl.hu.bep3.order.Domain.ValueObjects.DishOrder;
import nl.hu.bep3.order.domain.Payment;
import nl.hu.bep3.order.domain.Review;
import nl.hu.bep3.order.domain.Status;

import java.util.List;
import java.util.UUID;

public class OrderResponseDTO {
    private UUID id;
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
