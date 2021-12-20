package nl.hu.bep3.order.infrastructure.repository.mango;

import nl.hu.bep3.order.Domain.Order;
import nl.hu.bep3.order.Domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class MongoDbOrderRepository implements OrderRepository {
    private final SpringDataMongoOrderRepository orderRepository;

    @Autowired
    public MongoDbOrderRepository(final SpringDataMongoOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(final Order order) {
        orderRepository.save(order);
    }
}
