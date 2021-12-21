package nl.hu.bep3.order.domain.service;

import nl.hu.bep3.order.domain.Order;

import java.util.UUID;

public interface OrderService {
    Order placeOrder();

    void setStatus(Long id, String status);

    void completeOrder(UUID id);

    void deleteProduct(UUID id, UUID productId);
}