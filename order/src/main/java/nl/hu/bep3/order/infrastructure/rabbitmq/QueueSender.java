package nl.hu.bep3.order.infrastructure.rabbitmq;

import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.OrderApplication;
import nl.hu.bep3.order.domain.Order;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

  private final RabbitTemplate customerTemplate;
  private final DirectExchange customerExchange;
  private final RabbitTemplate orderTemplate;
  private final DirectExchange orderExchange;

  
  
  public QueueSender(
      RabbitTemplate customerTemplate,
      @Qualifier("directCustomerExchange") DirectExchange customerExchange,
      @Qualifier("directOrderExchange") DirectExchange OrderExchange,
      RabbitTemplate OrderTemplate,
      RabbitTemplate orderTemplate, DirectExchange orderExchange) {
    this.customerTemplate = customerTemplate;
    this.customerExchange = customerExchange;
    this.OrderTemplate = orderTemplate;
    this.OrderExchange = orderExchange;
  }

  public Customer getCustomer(final UUID id) {
    System.out.println("Request Customer with id: " + id);
    String message =
        (String) customerTemplate.convertSendAndReceive(customerExchange.getName(), "customer", id);
    System.out.println("Response: " + message);
    return OrderApplication.GSON.fromJson(message, Customer.class);
  }

  public void sendOrder(Order order) {
    final String message = OrderApplication.GSON.toJson(new OrderOutDto(order));
    this.orderTemplate.convertAndSend(this.queue.getName(), message);
    System.out.println(" [x] Sent '" + message + "'");
  }
}
