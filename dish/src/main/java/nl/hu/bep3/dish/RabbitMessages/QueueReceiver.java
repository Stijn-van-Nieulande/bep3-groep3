package nl.hu.bep3.dish.RabbitMessages;

import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.adapter.IngredientRepository;
import nl.hu.bep3.dish.application.DishApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {
  private final DishApplicationService dishApplicationService;

  public QueueReceiver(
      DishApplicationService dishApplicationService, IngredientRepository ingredientRepository) {
    this.dishApplicationService = dishApplicationService;
  }

  @RabbitListener(queues = "dishIn")
  public void receive(final String message) {
    System.out.println(" [x] Received '" + message + "'");
  }

  @RabbitListener(queues = "bep.dish.requests")
  public String getDish(final Long id) {
    final String message = DishApplication.GSON.toJson(dishApplicationService.getDishById(id));
    return message;
  }

  @RabbitListener(queues = "bep.dish.ingredient")
  public String getIngredient(final Long id) {
    final String message =
        DishApplication.GSON.toJson(dishApplicationService.getIngredientById(id));
    return message;
  }
}
