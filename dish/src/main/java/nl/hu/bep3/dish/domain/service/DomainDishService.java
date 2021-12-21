package nl.hu.bep3.dish.domain.service;

import java.util.ArrayList;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.request.IngredientAmountInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Exceptions.DishNotFoundException;
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
  public DishOutDto getDishById(UUID id) {
    Dish dish = dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
    return new DishOutDto(dish);
  }


  @Override
  public DishOutDto createDish(DishInDto dishInDto){
    ArrayList<IngredientAmount> ingredients = new ArrayList<>();
    if (dishInDto.ingredients == null || dishInDto.ingredients.isEmpty()){
      //TODO: throw exception
    }
    for (IngredientAmountInDto ingredientAmount : dishInDto.ingredients){
      Ingredient ingredient = ingredientService.getIngredientById(ingredientAmount.ingredientId);
      ingredients.add(new IngredientAmount(ingredientAmount.amount, ingredientAmount.amountUnit, ingredient));
    }
    Dish dish = new Dish(dishInDto.name, dishInDto.price, ingredients);
    dishRepository.save(dish);

    return new DishOutDto(dish);
  }


}
