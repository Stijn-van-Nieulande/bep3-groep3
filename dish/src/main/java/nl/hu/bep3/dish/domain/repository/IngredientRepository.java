package nl.hu.bep3.dish.domain.repository;

import nl.hu.bep3.dish.domain.Ingredient;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository {
    Optional<Ingredient> findById(UUID id);

    Ingredient save(Ingredient ingredient);
}
