package nl.hu.bep3.dish.infrastructure.RabbitMessages;

import java.util.ArrayList;
import java.util.UUID;
import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.IngredientOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.dish.domain.service.DishService;
import nl.hu.bep3.dish.domain.service.IngredientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {

  private final DishService dishService;
  private final IngredientService ingredientService;

  public QueueReceiver(final DishService dishService, final IngredientService ingredientService) {
    this.dishService = dishService;
    this.ingredientService = ingredientService;
  }

  @RabbitListener(queues = "bep.dish.dish")
  public String getDish(final UUID id) {
    final DishOutDto dish = new DishOutDto(this.dishService.getDishById(id));

    return DishApplication.GSON.toJson(dish);
  }

  @RabbitListener(queues = "bep.dish.ingredient")
  public String getIngredient(final UUID id) {
    final IngredientOutDto ingredient =
        new IngredientOutDto(this.ingredientService.getIngredientById(id));
    return DishApplication.GSON.toJson(ingredient);
  }

  @RabbitListener(queues = "bep.dish.menu")
  public String getMenu(final ArrayList<UUID> dishIds) {
    final ArrayList<DishOutDto> dishes = new ArrayList<>();

    for (final UUID id : dishIds) {
      dishes.add(new DishOutDto(this.dishService.getDishById(id)));
    }

    final MenuDto menuDto = new MenuDto();
    menuDto.menu = dishes;
    return DishApplication.GSON.toJson(menuDto);
  }
}
