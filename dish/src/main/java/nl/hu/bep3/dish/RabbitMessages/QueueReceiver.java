package nl.hu.bep3.dish.RabbitMessages;

import nl.hu.bep3.dish.application.DishApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {
  private final DishApplicationService dishApplicationService;

  public QueueReceiver(DishApplicationService dishApplicationService) {
    this.dishApplicationService = dishApplicationService;
  }

  @RabbitListener(queues = "dishIn")
  public void receive(final String message) {
    System.out.println(" [x] Received '" + message + "'");
  }
}
