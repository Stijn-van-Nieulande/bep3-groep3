package nl.hu.bep3.order.infrastructure.rabbitmq;

import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.OrderApplication;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

  private final RabbitTemplate customerTemplate;
  private final DirectExchange customerExchange;

  public QueueSender(
      RabbitTemplate customerTemplate,
      @Qualifier("directCustomerExchange") DirectExchange customerExchange) {
    this.customerTemplate = customerTemplate;
    this.customerExchange = customerExchange;
  }

  public Customer getCustomer(final UUID id) {
    System.out.println("Request Customer with id: " + id);
    String message =
        (String) customerTemplate.convertSendAndReceive(customerExchange.getName(), "customer", id);
    System.out.println("Response: " + message);
    return OrderApplication.GSON.fromJson(message, Customer.class);
  }
}
