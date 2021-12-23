package nl.hu.bep3.customer.infrastructure.rabbitmq;

import nl.hu.bep3.customer.CustomerApplication;
import nl.hu.bep3.customer.application.response.CustomerOutDto;
import nl.hu.bep3.customer.domain.Customer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

  private final RabbitTemplate template;
  private final Queue queue;

  public QueueSender(
      final RabbitTemplate template, @Qualifier("customerOutQueue") final Queue queue) {
    this.template = template;
    this.queue = queue;
  }

  public void sendCustomer(final Customer customer) {
    final String message = CustomerApplication.GSON.toJson(new CustomerOutDto(customer));

    this.template.convertAndSend(this.queue.getName(), message);
  }
}
