package nl.hu.bep3.dish.infrastructure.repository;

import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MongoDbIngredientRepository implements IngredientRepository {
    private final SpringDataMongoIngredientRepository springDataMongoIngredientRepository;

    @Autowired
    public MongoDbIngredientRepository(final SpringDataMongoIngredientRepository springDataMongoIngredientRepository) {
        this.springDataMongoIngredientRepository = springDataMongoIngredientRepository;
    }
    @Override
    public Optional<Ingredient> findById(final UUID id) {
        return springDataMongoIngredientRepository.findById(id);
    }

    @Override
    public Ingredient save(final Ingredient ingredient) {
        return springDataMongoIngredientRepository.save(ingredient);
    }
}
