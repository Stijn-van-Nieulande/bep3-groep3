package nl.hu.bep3.dish.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.dish.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoIngredientRepository extends MongoRepository<Ingredient, UUID> {

  Optional<Ingredient> findFirstByName(String name);
}
