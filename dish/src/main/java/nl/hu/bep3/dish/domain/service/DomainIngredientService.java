package nl.hu.bep3.dish.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.domain.Exceptions.IngredientNotFoundException;
import nl.hu.bep3.dish.domain.Exceptions.InvalidIngredientException;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;

public class DomainIngredientService implements IngredientService {

  private final IngredientRepository ingredientRepository;

  public DomainIngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public Ingredient createIngredient(IngredientInDto ingredientInDto) {
    if (ingredientRepository.findFirstByName(ingredientInDto.name).isPresent()) {
      throw new InvalidIngredientException("There is already an ingredient with the given name");
    }
    Ingredient ingredient = new Ingredient(ingredientInDto.name, ingredientInDto.allergies);
    return ingredientRepository.save(ingredient);
  }

  public Ingredient getIngredientById(UUID id) {
    return ingredientRepository
        .findById(id)
        .orElseThrow(
            () -> new IngredientNotFoundException("No ingredient found with id: " + id));
  }

  @Override
  public Ingredient updateIngredient(UUID id, IngredientInDto ingredientInDto) {
    final Ingredient ingredient =
        ingredientRepository
            .findById(id)
            .orElseThrow(
                () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    ingredient.setName(ingredientInDto.name);
    ingredient.setAllergies(ingredientInDto.allergies);
    ingredientRepository.save(ingredient);
    return ingredient;
  }

  @Override
  public void deleteIngredient(UUID id) {
    final Ingredient ingredient =
        ingredientRepository
            .findById(id)
            .orElseThrow(
                () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    ingredientRepository.delete(ingredient);
  }

  @Override
  public List<Ingredient> getAllIngredients() {
    return ingredientRepository.findAll();
  }

}
