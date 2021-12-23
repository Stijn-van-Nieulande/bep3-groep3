package nl.hu.bep3.dish.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.exceptions.IngredientNotFoundException;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;

public class DomainIngredientService implements IngredientService {

  private final IngredientRepository ingredientRepository;

  public DomainIngredientService(final IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public Ingredient createIngredient(final IngredientInDto ingredientInDto) {
    final Ingredient ingredient = new Ingredient(ingredientInDto.name, ingredientInDto.allergies);
    return this.ingredientRepository.save(ingredient);
  }

  public Ingredient getIngredientById(final UUID id) {
    return this.ingredientRepository
        .findById(id)
        .orElseThrow(() -> new IngredientNotFoundException("No ingredient found with id: " + id));
  }

  @Override
  public Ingredient updateIngredient(final UUID id, final IngredientInDto ingredientInDto) {
    final Ingredient ingredient =
        this.ingredientRepository
            .findById(id)
            .orElseThrow(
                () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    ingredient.setName(ingredientInDto.name);
    ingredient.setAllergies(ingredientInDto.allergies);
    this.ingredientRepository.save(ingredient);
    return ingredient;
  }

  @Override
  public void deleteIngredient(final UUID id) {
    final Ingredient ingredient =
        this.ingredientRepository
            .findById(id)
            .orElseThrow(
                () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    this.ingredientRepository.delete(ingredient);
  }

  @Override
  public List<Ingredient> getAllIngredients() {
    return this.ingredientRepository.findAll();
  }
}
