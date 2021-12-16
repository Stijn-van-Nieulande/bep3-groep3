package nl.hu.bep3.kitchen.adapter.repositories;

import nl.hu.bep3.kitchen.domain.Kitchen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends MongoRepository<Kitchen, Long> {

}
