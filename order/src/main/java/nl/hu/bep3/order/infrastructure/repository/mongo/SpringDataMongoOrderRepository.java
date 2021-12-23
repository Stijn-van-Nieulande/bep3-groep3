package nl.hu.bep3.order.infrastructure.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import nl.hu.bep3.order.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataMongoOrderRepository extends MongoRepository<Order, UUID> {

  public List<Order> findOrderByCustomerId(UUID id);
}
