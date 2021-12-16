package nl.hu.bep3.dish.adapter;

import nl.hu.bep3.dish.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, Long> {}
