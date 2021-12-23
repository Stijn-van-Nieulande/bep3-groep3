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
  public MongoDbIngredientRepository(
      final SpringDataMongoIngredientRepository springDataMongoIngredientRepository) {
    this.springDataMongoIngredientRepository = springDataMongoIngredientRepository;
  }

  @Override
  public Optional<Ingredient> findById(final UUID id) {
    return this.springDataMongoIngredientRepository.findById(id);
  }

  @Override
  public Ingredient save(final Ingredient ingredient) {
    return this.springDataMongoIngredientRepository.save(ingredient);
  }

  @Override
  public List<Ingredient> findAll() {
    return this.springDataMongoIngredientRepository.findAll();
  }

  @Override
  public Optional<Ingredient> findFirstByName(final String name) {
    return this.springDataMongoIngredientRepository.findFirstByName(name);
  }

  @Override
  public void delete(final Ingredient ingredient) {
    this.springDataMongoIngredientRepository.delete(ingredient);
  }
}
