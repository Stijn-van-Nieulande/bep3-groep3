package nl.hu.bep3.kitchen.infrastructure.rabbitmq;

import nl.hu.bep3.kitchen.KitchenApplication;
import nl.hu.bep3.kitchen.application.response.OrderResponseDto;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class QueueReceiver {

  private KitchenService kitchenService;

  public QueueReceiver(KitchenService kitchenService) {
    this.kitchenService = kitchenService;
  }


  @RabbitListener(queues = "order.addOrder")
  public void addNewOrder(String message) {
    OrderResponseDto orderResponseDto = KitchenApplication.GSON.fromJson(message, OrderResponseDto.class);
    kitchenService.addOrder(orderResponseDto, orderResponseDto.id);
  }
}
