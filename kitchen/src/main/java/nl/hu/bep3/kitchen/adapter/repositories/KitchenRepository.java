package nl.hu.bep3.kitchen.adapter.repositories;

import nl.hu.bep3.kitchen.domain.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {
}
