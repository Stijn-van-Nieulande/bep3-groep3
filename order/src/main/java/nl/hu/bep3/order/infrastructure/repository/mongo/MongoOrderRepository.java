package nl.hu.bep3.order.infrastructure.repository.mongo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.repository.OrderRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoOrderRepository implements OrderRepository {

  private final SpringDataMongoOrderRepository orderRepository;

  public MongoOrderRepository(final SpringDataMongoOrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public Optional<Order> findById(final UUID id) {
    return this.orderRepository.findById(id);
  }

  @Override
  public Order save(final Order order) {
    this.orderRepository.save(order);
    return order;
  }

  @Override
  public void deleteById(final UUID id) {
    this.orderRepository.deleteById(id);
  }

  @Override
  public List<Order> findOrdersFromCustomer(final UUID customerId) {
    return this.orderRepository.findOrderByCustomerId(customerId);
  }
}
