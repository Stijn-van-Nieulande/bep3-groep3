package nl.hu.bep3.kitchen.infrastructure.rabbitmq;

import nl.hu.bep3.kitchen.KitchenApplication;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import nl.hu.bep3.order.application.response.OrderResponseToKitchenDTO;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {

  private KitchenService kitchenService;

  public QueueReceiver(KitchenService kitchenService) {
    this.kitchenService = kitchenService;
  }

  @RabbitListener(queues = "order.addOrder")
  public void addNewOrder(String message) {
    try {
      System.out.println("addnewOrder in quereciever van kitchen");
      OrderResponseToKitchenDTO orderDto =
          KitchenApplication.GSON.fromJson(message, OrderResponseToKitchenDTO.class);
      kitchenService.addOrder(orderDto, orderDto.kitchenId);
    } catch (Exception e) {
      throw new AmqpRejectAndDontRequeueException(e);
    }
  }
}
