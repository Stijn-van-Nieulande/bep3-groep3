package nl.hu.bep3.kitchen.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.kitchen.domain.Kitchen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoKitchenRepository extends MongoRepository<Kitchen, UUID> {
    Optional<Kitchen> findFirstByAddress(String address);
}
