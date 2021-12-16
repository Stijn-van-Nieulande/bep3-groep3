package nl.hu.bep3.dish.adapter;

import nl.hu.bep3.dish.domain.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends MongoRepository<Dish, Long> {}
