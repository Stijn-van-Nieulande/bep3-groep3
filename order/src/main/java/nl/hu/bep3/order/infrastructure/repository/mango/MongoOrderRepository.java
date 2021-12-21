package nl.hu.bep3.order.infrastructure.repository.mango;

import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.repository.OrderRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MongoOrderRepository implements OrderRepository {
    private final SpringDataMongoOrderRepository orderRepository;

    public MongoOrderRepository(final SpringDataMongoOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(final Order order) {
        orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteById(final UUID id) {
        this.orderRepository.deleteById(id);
    }
}
