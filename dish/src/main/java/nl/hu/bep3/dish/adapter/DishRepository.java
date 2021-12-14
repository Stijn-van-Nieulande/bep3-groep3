package nl.hu.bep3.dish.adapter;

import nl.hu.bep3.dish.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {}
