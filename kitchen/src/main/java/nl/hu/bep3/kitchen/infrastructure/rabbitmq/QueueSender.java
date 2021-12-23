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

  public QueueSender(RabbitTemplate dishTemplate,
      RabbitTemplate ingredientTemplate,
      RabbitTemplate menuTemplate,
      @Qualifier("directDishExchange") DirectExchange dishExchange,
      @Qualifier("directIngredientExchange") DirectExchange ingredientExchange,
      @Qualifier("directMenuExchange") DirectExchange menuExchange) {
    this.dishTemplate = dishTemplate;
    this.ingredientTemplate = ingredientTemplate;
    this.menuTemplate = menuTemplate;
    this.dishExchange = dishExchange;
    this.ingredientExchange = ingredientExchange;
    this.menuExchange = menuExchange;
  }

  public String getDish(final UUID id) {
    System.out.println("Request dish with id: " + id);
    String message = (String) dishTemplate.convertSendAndReceive(dishExchange.getName(), "dish",
        id);
    System.out.println("Response: " + message);
    return message;
  }

  public String getIngredient(final UUID id) {
    System.out.println("Request ingredient with id: " + id);
    String message = (String) ingredientTemplate.convertSendAndReceive(ingredientExchange.getName(),
        "ingredient", id);
    System.out.println("Response: " + message);
    return message;
  }

  public MenuDto getMenu(List<UUID> menu) {
    System.out.println("Request menu");
    String message = (String) menuTemplate.convertSendAndReceive(menuExchange.getName(),
        "ingredient", menu);
    System.out.println("Response: " + message);
    MenuDto responseMenu = KitchenApplication.GSON.fromJson(message, MenuDto.class);
    return responseMenu;
  }

  public DishOutDto addDish(UUID kichenId, DishInDto dto) {
    System.out.println("Request Addition of dish to kitchen with id: " + kichenId);
    String message = (String) dishTemplate.convertSendAndReceive(dishExchange);
    System.out.println("Response: " + message);
    return null;
  }
}
