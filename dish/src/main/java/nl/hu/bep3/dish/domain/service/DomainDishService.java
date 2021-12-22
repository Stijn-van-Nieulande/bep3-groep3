package nl.hu.bep3.dish.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientAmountInDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Exceptions.DishNotFoundException;
import nl.hu.bep3.dish.domain.Exceptions.InvalidIngredientException;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.IngredientAmount;
import nl.hu.bep3.dish.domain.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DomainDishService implements DishService {

  private final DishRepository dishRepository;
  private final IngredientService ingredientService;

  public DomainDishService(
      DishRepository dishRepository, IngredientService ingredientService) {
    this.dishRepository = dishRepository;
    this.ingredientService = ingredientService;
  }

  @Override
  public Dish getDishById(UUID id) {
    return dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
  }

  @Override
  public Dish createDish(DishInDto dishInDto) {
    ArrayList<IngredientAmount> ingredients = new ArrayList<>();
    if (dishInDto.ingredients == null || dishInDto.ingredients.isEmpty()) {
      throw new InvalidIngredientException("There where no ingredients in the dish");
      //TODO: throw exception
    }
    for (IngredientAmountInDto ingredientAmount : dishInDto.ingredients) {
      Ingredient ingredient = ingredientService.getIngredientById(ingredientAmount.ingredientId);
      ingredients.add(
          new IngredientAmount(ingredientAmount.amount, ingredientAmount.amountUnit, ingredient));
    }
    Dish dish = new Dish(dishInDto.name, dishInDto.price, ingredients);
    return dishRepository.save(dish);
  }

  @Override
  public Dish updateDish(UUID id, DishInDto dishInDto) {
    Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
    List<IngredientAmount> ingredientAmounts = new ArrayList<>();
    for (IngredientAmountInDto i : dishInDto.ingredients) {
      Ingredient ingredient = ingredientService.getIngredientById(i.ingredientId);
      ingredientAmounts.add(new IngredientAmount(i.amount, i.amountUnit, ingredient));
    }
    dish.setIngredients(ingredientAmounts);
    dish.setName(dishInDto.name);
    dish.setPrice(dishInDto.price);
    return dishRepository.save(dish);
  }

  @Override
  public void deleteDish(UUID id) {
    Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
    dishRepository.delete(dish);
  }

}
