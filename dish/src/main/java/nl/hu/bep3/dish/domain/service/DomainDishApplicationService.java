package nl.hu.bep3.dish.domain.service;

import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientAmountInDto;
import nl.hu.bep3.dish.application.request.IngredientInDto;
import nl.hu.bep3.dish.domain.IngredientAmount;
import nl.hu.bep3.dish.domain.repository.DishRepository;
import nl.hu.bep3.dish.domain.repository.IngredientRepository;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.domain.Exceptions.DishNotFoundException;
import nl.hu.bep3.dish.domain.Exceptions.IngredientNotFoundException;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class DomainDishApplicationService implements DishApplicationService {
  private final DishRepository dishRepository;
  private final IngredientRepository ingredientRepository;

  public DomainDishApplicationService(
          DishRepository dishRepository, IngredientRepository ingredientRepository) {
    this.dishRepository = dishRepository;
    this.ingredientRepository = ingredientRepository;
  }

  public DishOutDto getDishById(UUID id) {
    Dish dish =
        dishRepository
            .findById(id)
            .orElseThrow(() -> new DishNotFoundException("No dish found with id: " + id));
    return new DishOutDto(dish);
  }

  public IngredientOutDto getIngredientById(UUID id) {
    final Ingredient ingredient =
        ingredientRepository
            .findById(id)
            .orElseThrow(
                    () -> new IngredientNotFoundException("No ingredient found with id: " + id));
    return new IngredientOutDto(ingredient);
  }

  @Override
  public DishOutDto getDishById(Long id) {
    return null;
  }

  @Override
  public IngredientOutDto getIngredientById(Long id) {
    return null;
  }

  @Override
  public DishOutDto createDish(DishInDto dishInDto){
    ArrayList<IngredientAmount> ingredients = new ArrayList<>();
    if (dishInDto.ingredients == null || dishInDto.ingredients.isEmpty()){
      //TODO: throw exception
    }
    for (IngredientAmountInDto ingredientAmount : dishInDto.ingredients){
      Ingredient ingredient = ingredientRepository.findById(ingredientAmount.ingredientId).orElseThrow(() -> new IngredientNotFoundException(ingredientAmount.ingredientId));
      ingredients.add(new IngredientAmount(ingredientAmount.amount, ingredientAmount.amountUnit, ingredient));
    }
    Dish dish = new Dish(dishInDto.name, dishInDto.price, ingredients);
    dishRepository.save(dish);

    return new DishOutDto(dish);
  }

  @Override
  public IngredientOutDto createIngredient(IngredientInDto ingredientInDto) {
    Ingredient ingredient = new Ingredient(ingredientInDto.name, ingredientInDto.allergies);
    System.out.println("2");
    ingredientRepository.save(ingredient);
    System.out.println("3");
    System.out.println(new IngredientOutDto(ingredient));
    System.out.println();
    return new IngredientOutDto(ingredient);
  }
}
