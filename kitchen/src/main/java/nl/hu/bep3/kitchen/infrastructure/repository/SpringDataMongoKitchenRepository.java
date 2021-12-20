package nl.hu.bep3.kitchen.infrastructure.repository;

import nl.hu.bep3.kitchen.domain.Kitchen;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataMongoKitchenRepository extends MongoRepository<Kitchen, ObjectId> {
    Optional<Kitchen> findFirstByAddress(String address);
}
