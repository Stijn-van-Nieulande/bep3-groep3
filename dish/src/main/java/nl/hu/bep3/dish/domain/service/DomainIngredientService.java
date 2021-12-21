package nl.hu.bep3.dish.domain.service;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.domain.Exceptions.IngredientNotFoundException;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;

public class DomainIngredientService implements IngredientService{
  private final IngredientRepository ingredientRepository;

  public DomainIngredientService(IngredientRepository ingredientRepository){
    this.ingredientRepository = ingredientRepository;
  }
  @Override
  public IngredientOutDto createIngredient(IngredientInDto ingredientInDto) {
    Ingredient ingredient = new Ingredient(ingredientInDto.name, ingredientInDto.allergies);
    ingredientRepository.save(ingredient);
    return new IngredientOutDto(ingredient);
  }

  public Ingredient getIngredientById(UUID id) {
    final Ingredient ingredient =
        ingredientRepository
            .findById(id)
            .orElseThrow(
                () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    return ingredient;
  }

  @Override
  public void deleteIngredient(UUID id){

  }

  @Override
  public List<Ingredient> getAllIngredients(){
    return ingredientRepository.findAll();
  }

}
