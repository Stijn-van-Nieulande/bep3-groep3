package nl.hu.bep3.kitchen.infrastructure.rabbitmq;

import nl.hu.bep3.kitchen.KitchenApplication;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import nl.hu.bep3.order.application.response.OrderResponseToKitchenDto;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {

  private final KitchenService kitchenService;

  public QueueReceiver(final KitchenService kitchenService) {
    this.kitchenService = kitchenService;
  }

  @RabbitListener(queues = "order.addOrder")
  public void addNewOrder(final String message) {
    try {
      final OrderResponseToKitchenDto orderDto =
          KitchenApplication.GSON.fromJson(message, OrderResponseToKitchenDto.class);
      this.kitchenService.addOrder(orderDto, orderDto.kitchenId);
    } catch (final Exception exception) {
      throw new AmqpRejectAndDontRequeueException(exception);
    }
  }
}
