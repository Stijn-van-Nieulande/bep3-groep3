package nl.hu.bep3.order.infrastructure.repository.mongo;

import java.util.UUID;
import nl.hu.bep3.order.domain.valueobjects.DishOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoDishOrderRepository extends MongoRepository<DishOrder, UUID> {}
