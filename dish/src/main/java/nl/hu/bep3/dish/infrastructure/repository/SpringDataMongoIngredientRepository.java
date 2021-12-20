package nl.hu.bep3.dish.infrastructure.repository;

import nl.hu.bep3.dish.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoIngredientRepository extends MongoRepository<Ingredient, UUID> {}
