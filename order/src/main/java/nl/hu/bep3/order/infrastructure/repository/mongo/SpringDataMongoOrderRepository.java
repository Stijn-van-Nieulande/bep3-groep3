package nl.hu.bep3.order.infrastructure.repository.mongo;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.order.domain.Order;
import nl.hu.bep3.order.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoOrderRepository extends MongoRepository<Order, UUID> {

  public List<Order> findOrderByCustomerId(UUID id);
}
