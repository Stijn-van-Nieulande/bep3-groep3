package nl.hu.bep3.order.domain.service;

import nl.hu.bep3.order.Aplication.request.OrderRequestDTO;
import nl.hu.bep3.order.Aplication.request.ReviewRequestDTO;
import nl.hu.bep3.order.Aplication.response.OrderResponseDTO;
import nl.hu.bep3.order.Aplication.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Order;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

  OrderResponseDTO placeNewOrder(OrderRequestDTO orderRequestDTO);

  void setStatus(UUID id, String status);

  void completeOrder(UUID id);

  void deleteProduct(UUID id);

  Order getOrderById(UUID orderId);

  ReviewResponseDTO setReview(UUID id, ReviewRequestDTO reviewRequestDTO);

  void deleteOrder(UUID id);

  List<OrderResponseDTO> getOrdersFromCustomer(UUID customerId);

  Float getAmount(UUID id);
}