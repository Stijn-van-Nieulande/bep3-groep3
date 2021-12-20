package nl.hu.bep3.dish.infrastructure.RabbitMessages;

import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.infrastructure.repository.SpringDataMongoIngredientRepository;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientAmountOutDto;
import nl.hu.bep3.dish.domain.service.DomainDishApplicationService;
import nl.hu.bep3.dish.domain.AmountUnit;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import nl.hu.bep3.dish.domain.IngredientAmount;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class QueueReceiver {
  private final DomainDishApplicationService domainDishApplicationService;

  public QueueReceiver(
          DomainDishApplicationService domainDishApplicationService, SpringDataMongoIngredientRepository ingredientRepository) {
    this.domainDishApplicationService = domainDishApplicationService;
  }

  @RabbitListener(queues = "bep.dish.dish")
  public String getDish(final UUID id) {
    System.out.println("dish: " + id);
    DishOutDto dish = new DishOutDto(new Dish("kaasplank", 1, null));
    //final String message = DishApplication.GSON.toJson(dishApplicationService.getDishById(id));
    final String message = DishApplication.GSON.toJson(dish);
    return message;
  }

  @RabbitListener(queues = "bep.dish.ingredient")
  public String getIngredient(final UUID id) {
    //final String message =
     //   DishApplication.GSON.toJson(dishApplicationService.getIngredientById(id));
    System.out.println("ingredient: " + id);
    IngredientAmountOutDto ingredient = new IngredientAmountOutDto(new IngredientAmount(10f, AmountUnit.KILOGRAM, new Ingredient("potato", null)));
    final String message = DishApplication.GSON.toJson(ingredient);
    return message;
  }

  @RabbitListener(queues = "bep.dish.menu")
  public String getMenu(final ArrayList<UUID> dishIds) {
    System.out.println("menu");
    ArrayList<DishOutDto> dishes = new ArrayList<>();
    for (UUID id : dishIds) {
      //findbyid
    }
    final String message = DishApplication.GSON.toJson(dishes);
    return message;
  }
}
