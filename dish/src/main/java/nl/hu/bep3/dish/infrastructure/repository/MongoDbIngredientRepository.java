package nl.hu.bep3.dish.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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

    @Override
  public List<Ingredient> findAll(){
      return springDataMongoIngredientRepository.findAll();
    }
}
