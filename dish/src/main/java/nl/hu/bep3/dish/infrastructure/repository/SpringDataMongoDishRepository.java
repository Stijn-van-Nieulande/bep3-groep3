package nl.hu.bep3.dish.infrastructure.repository;

import java.util.UUID;
import nl.hu.bep3.dish.domain.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoDishRepository extends MongoRepository<Dish, UUID> {

}
