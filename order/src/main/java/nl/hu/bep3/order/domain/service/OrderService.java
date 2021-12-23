package nl.hu.bep3.order.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import nl.hu.bep3.order.application.request.OrderRequestDTO;
import nl.hu.bep3.order.application.request.ReviewRequestDTO;
import nl.hu.bep3.order.application.response.OrderResponseDTO;
import nl.hu.bep3.order.application.response.ReviewResponseDTO;
import nl.hu.bep3.order.domain.Order;

public interface OrderService {

  OrderResponseDTO placeNewOrder(OrderRequestDTO orderRequestDTO);

  void setStatus(UUID id, String status);

  void completeOrder(UUID id);

  void deleteProduct(UUID id);

  Order getOrderById(UUID orderId);

  Page<Review> getReviewsPaginated(Pageable pageable);

  ReviewResponseDTO setReview(UUID id, ReviewRequestDTO reviewRequestDTO);

  void deleteOrder(UUID id);

  List<OrderResponseDTO> getOrdersFromCustomer(UUID customerId);

  Float getAmount(UUID id);
}
