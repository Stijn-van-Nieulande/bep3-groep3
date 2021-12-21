package nl.hu.bep3.order.domain.service;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.Aplication.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Order;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface OrderService {

  Order placeNewOrder(OrderRequestDTO orderRequestDTO);

  void setStatus(UUID id, String status);

  void completeOrder(UUID id);

  void deleteProduct(UUID id);

  Order getOrderById(UUID orderId);

  ReviewResponseDTO setReview(UUID id, ReviewRequestDTO reviewRequestDTO);

  void deleteOrder(UUID id);
}