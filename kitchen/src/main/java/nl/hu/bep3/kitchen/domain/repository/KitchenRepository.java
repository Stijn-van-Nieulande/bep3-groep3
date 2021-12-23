package nl.hu.bep3.kitchen.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.kitchen.domain.Kitchen;

public interface KitchenRepository {

  Optional<Kitchen> findById(UUID id);

  Kitchen save(Kitchen kitchen);

  void delete(Kitchen kitchen);

  Optional<Kitchen> findFirstByAddress(String address);

  List<Kitchen> findAll();
}
