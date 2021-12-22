package nl.hu.bep3.order.infrastructure.rabbitmq;

import java.util.List;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class QueueSender {

  private final RabbitTemplate customerTemplate;
  private final DirectExchange customerExchange;


  public QueueSender(RabbitTemplate customerTemplate,
      @Qualifier("directCustomerExchange") DirectExchange customerExchange) {
    this.customerTemplate = customerTemplate;
    this.customerExchange = customerExchange;
  }

  public String getCustomer(final UUID id) {
    System.out.println("Request Customer with id: " + id);
    String message = (String) customerTemplate.convertSendAndReceive(customerExchange.getName(),
        "customer", id);
    System.out.println("Response: " + message);
    return message;
  }
}


