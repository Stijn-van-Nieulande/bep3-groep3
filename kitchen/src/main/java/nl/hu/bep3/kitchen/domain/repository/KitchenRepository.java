package nl.hu.bep3.kitchen.domain.repository;

import nl.hu.bep3.kitchen.domain.Kitchen;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface KitchenRepository {
    Optional<Kitchen> findById(ObjectId id);

    Kitchen save(Kitchen kitchen);

    Optional<Kitchen> findFirstByAddress(String address);
}
