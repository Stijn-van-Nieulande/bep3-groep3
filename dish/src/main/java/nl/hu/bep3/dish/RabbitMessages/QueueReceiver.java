package nl.hu.bep3.dish.RabbitMessages;

import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.adapter.IngredientRepository;
import nl.hu.bep3.dish.adapter.dto.DishOutDto;
import nl.hu.bep3.dish.adapter.dto.IngredientAmountOutDto;
import nl.hu.bep3.dish.application.DishApplicationService;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.IngredientAmount;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QueueReceiver {
  private final DishApplicationService dishApplicationService;

  public QueueReceiver(
      DishApplicationService dishApplicationService, IngredientRepository ingredientRepository) {
    this.dishApplicationService = dishApplicationService;
  }

  @RabbitListener(queues = "bep.dish.dish")
  public String getDish(final Long id) {
    System.out.println("dish: " + id);
    DishOutDto dish = new DishOutDto(new Dish("kaasplank", 1, null));
    //final String message = DishApplication.GSON.toJson(dishApplicationService.getDishById(id));
    final String message = DishApplication.GSON.toJson(dish);
    return message;
  }

  @RabbitListener(queues = "bep.dish.ingredient")
  public String getIngredient(final Long id) {
    //final String message =
     //   DishApplication.GSON.toJson(dishApplicationService.getIngredientById(id));
    System.out.println("ingredient: " + id);
    IngredientAmountOutDto ingredient = new IngredientAmountOutDto(new IngredientAmount(10f, AmountUnit.KILOGRAM, new Ingredient("potato", null)));
    final String message = DishApplication.GSON.toJson(ingredient);
    return message;
  }
}
