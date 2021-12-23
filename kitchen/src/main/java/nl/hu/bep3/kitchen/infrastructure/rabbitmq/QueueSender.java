package nl.hu.bep3.kitchen.infrastructure.rabbitmq;

import java.util.List;
import java.util.UUID;
import nl.hu.bep3.dish.application.request.DishInDto;
import nl.hu.bep3.dish.application.response.DishOutDto;
import nl.hu.bep3.dish.application.response.MenuDto;
import nl.hu.bep3.kitchen.KitchenApplication;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

  private final RabbitTemplate dishTemplate;
  private final RabbitTemplate ingredientTemplate;
  private final RabbitTemplate menuTemplate;
  private final DirectExchange dishExchange;
  private final DirectExchange ingredientExchange;
  private final DirectExchange menuExchange;

  public QueueSender(
      final RabbitTemplate dishTemplate,
      final RabbitTemplate ingredientTemplate,
      final RabbitTemplate menuTemplate,
      @Qualifier("directDishExchange") final DirectExchange dishExchange,
      @Qualifier("directIngredientExchange") final DirectExchange ingredientExchange,
      @Qualifier("directMenuExchange") final DirectExchange menuExchange) {
    this.dishTemplate = dishTemplate;
    this.ingredientTemplate = ingredientTemplate;
    this.menuTemplate = menuTemplate;
    this.dishExchange = dishExchange;
    this.ingredientExchange = ingredientExchange;
    this.menuExchange = menuExchange;
  }

  public String getDish(final UUID id) {
    return (String)
        this.dishTemplate.convertSendAndReceive(this.dishExchange.getName(), "dish", id);
  }

  public String getIngredient(final UUID id) {
    return (String)
        this.ingredientTemplate.convertSendAndReceive(
            this.ingredientExchange.getName(), "ingredient", id);
  }

  public MenuDto getMenu(final List<UUID> menu) {
    final String message =
        (String)
            this.menuTemplate.convertSendAndReceive(
                this.menuExchange.getName(), "ingredient", menu);
    return KitchenApplication.GSON.fromJson(message, MenuDto.class);
  }

  public DishOutDto addDish(final UUID kichenId, final DishInDto dto) {
    final String message = (String) this.dishTemplate.convertSendAndReceive(this.dishExchange);
    return null;
  }
}
