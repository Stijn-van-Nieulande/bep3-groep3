package nl.hu.bep3.order.domain.service;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.domain.Order;

import java.util.UUID;

public interface OrderService {

    Order placeNewOrder(OrderRequestDTO orderRequestDTO);

    void setStatus(UUID id, String status);

    void completeOrder(UUID id);

    void deleteProduct(UUID id);
}