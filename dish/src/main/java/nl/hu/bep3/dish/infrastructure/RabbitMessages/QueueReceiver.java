package nl.hu.bep3.dish.infrastructure.RabbitMessages;

import java.util.ArrayList;
import java.util.UUID;
import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientAmountOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.IngredientAmount;
import nl.hu.bep3.dish.domain.service.DishService;
import nl.hu.bep3.dish.domain.service.IngredientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {

  private final DishService dishService;
  private final IngredientService ingredientService;

  public QueueReceiver(
      DishService dishService, IngredientService ingredientService) {
    this.dishService = dishService;
    this.ingredientService = ingredientService;
  }

  @RabbitListener(queues = "bep.dish.dish")
  public String getDish(final UUID id) {
    System.out.println("dish: " + id);
    DishOutDto dish = new DishOutDto(dishService.getDishById(id));

    //final String message = DishApplication.GSON.toJson(dishApplicationService.getDishById(id));
    return DishApplication.GSON.toJson(dish);
  }

  @RabbitListener(queues = "bep.dish.ingredient")
  public String getIngredient(final UUID id) {
    //final String message =
    //   DishApplication.GSON.toJson(dishApplicationService.getIngredientById(id));
    System.out.println("ingredient: " + id);
    IngredientOutDto ingredient = new IngredientOutDto(ingredientService.getIngredientById(id));
    return DishApplication.GSON.toJson(ingredient);
  }

  @RabbitListener(queues = "bep.dish.menu")
  public String getMenu(final ArrayList<UUID> dishIds) {
    System.out.println("menu");
    ArrayList<DishOutDto> dishes = new ArrayList<>();
    for (UUID id : dishIds) {
      dishes.add(new DishOutDto(dishService.getDishById(id)));
    }
    MenuDto menuDto = new MenuDto();
    menuDto.menu = dishes;
    return DishApplication.GSON.toJson(menuDto);
  }
}
