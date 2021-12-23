package nl.hu.bep3.order.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.application.request.OrderRequestDto;
import nl.hu.bep3.order.application.request.ReviewRequestDto;
import nl.hu.bep3.order.application.response.OrderResponseDto;
import nl.hu.bep3.order.application.response.ReviewResponseDto;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  OrderResponseDto placeNewOrder(OrderRequestDto orderRequestDto);

  void setStatus(UUID id, String status);

  Order getOrderById(UUID orderId);

  Page<Review> getReviewsPaginated(Pageable pageable);

  ReviewResponseDto setReview(UUID id, ReviewRequestDto reviewRequestDto);

  void deleteOrder(UUID id);

  List<OrderResponseDto> getOrdersFromCustomer(UUID customerId);

  double getAmount(UUID id);

  void sendOrder(Order order);
}
