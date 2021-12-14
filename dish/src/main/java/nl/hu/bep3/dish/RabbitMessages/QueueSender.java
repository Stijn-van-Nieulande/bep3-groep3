package nl.hu.bep3.dish.RabbitMessages;

import nl.hu.bep3.dish.DishApplication;
import nl.hu.bep3.dish.adapter.dto.DishOutDto;
import nl.hu.bep3.dish.adapter.dto.IngredientDto;
import nl.hu.bep3.dish.domain.Dish;
import nl.hu.bep3.dish.domain.Ingredient;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
  private final RabbitTemplate template;
  private final Queue queue;

  public QueueSender(final RabbitTemplate template, @Qualifier("dishOutQueue") final Queue queue) {
    this.template = template;
    this.queue = queue;
  }

  public void sendDish(Dish dish) {
    final String message = DishApplication.GSON.toJson(new DishOutDto(dish));

    this.template.convertAndSend(this.queue.getName(), message);
    System.out.println(" [x] Sent '" + message + "'");
  }

  public void sendIngredient(Ingredient ingredient) {
    IngredientDto ingredientDto = new IngredientDto(ingredient);

    final String message = DishApplication.GSON.toJson(ingredientDto);

    this.template.convertAndSend(this.queue.getName(), message);
    System.out.println(" [x] Sent '" + message + "'");
  }
}
