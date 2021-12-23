package nl.hu.bep3.dish.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientAmountInDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.IngredientAmount;
import nl.hu.bep3.dish.domain.exceptions.DishNotFoundException;
import nl.hu.bep3.dish.domain.exceptions.InvalidIngredientException;
import nl.hu.bep3.dish.domain.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DomainDishService implements DishService {

  private final DishRepository dishRepository;
  private final IngredientService ingredientService;

  public DomainDishService(
      final DishRepository dishRepository, final IngredientService ingredientService) {
    this.dishRepository = dishRepository;
    this.ingredientService = ingredientService;
  }

  @Override
  public Dish getDishById(final UUID id) {
    return this.dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
  }

  @Override
  public Dish createDish(final DishInDto dishInDto) {
    final ArrayList<IngredientAmount> ingredients = new ArrayList<>();
    if (dishInDto.ingredients == null || dishInDto.ingredients.isEmpty()) {
      throw new InvalidIngredientException("There where no ingredients in the dish");
    }
    for (final IngredientAmountInDto ingredientAmount : dishInDto.ingredients) {
      final Ingredient ingredient =
          this.ingredientService.getIngredientById(ingredientAmount.ingredientId);
      ingredients.add(
          new IngredientAmount(ingredientAmount.amount, ingredientAmount.amountUnit, ingredient));
    }
    final Dish dish = new Dish(dishInDto.name, dishInDto.price, ingredients);
    return this.dishRepository.save(dish);
  }

  @Override
  public Dish updateDish(final UUID id, final DishInDto dishInDto) {
    final Dish dish =
        this.dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
    final List<IngredientAmount> ingredientAmounts = new ArrayList<>();
    for (final IngredientAmountInDto i : dishInDto.ingredients) {
      final Ingredient ingredient = this.ingredientService.getIngredientById(i.ingredientId);
      ingredientAmounts.add(new IngredientAmount(i.amount, i.amountUnit, ingredient));
    }
    dish.setIngredients(ingredientAmounts);
    dish.setName(dishInDto.name);
    dish.setPrice(dishInDto.price);
    return this.dishRepository.save(dish);
  }

  @Override
  public void deleteDish(final UUID id) {
    final Dish dish =
        this.dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
    this.dishRepository.delete(dish);
  }
}
