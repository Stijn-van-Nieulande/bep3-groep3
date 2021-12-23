package nl.hu.bep3.order.domain.exception;

import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  Optional<Order> findById(UUID id);

  Page<Review> findAllReviewsPaginated(Pageable pageable);

  Order save(Order employee);

  void deleteById(UUID uuid);

  List<Order> findOrdersFromCustomer(UUID customerId);
}
