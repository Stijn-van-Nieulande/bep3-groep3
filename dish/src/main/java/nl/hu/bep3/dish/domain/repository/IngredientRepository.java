package nl.hu.bep3.dish.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.dish.domain.Ingredient;

public interface IngredientRepository {
    Optional<Ingredient> findById(UUID id);

    Ingredient save(Ingredient ingredient);

    List<Ingredient> findAll();
}
