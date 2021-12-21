package nl.hu.bep3.kitchen.domain.repository;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.kitchen.domain.Kitchen;

public interface KitchenRepository {
    Optional<Kitchen> findById(UUID id);

    Kitchen save(Kitchen kitchen);

    Optional<Kitchen> findFirstByAddress(String address);
}
