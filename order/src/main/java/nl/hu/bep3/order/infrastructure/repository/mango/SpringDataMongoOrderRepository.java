package nl.hu.bep3.order.infrastructure.repository.mango;
import org.springframework.data.mongodb.repository.MongoRepository;
import nl.hu.bep3.order.Domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoOrderRepository extends MongoRepository<Order, Long> {
}
