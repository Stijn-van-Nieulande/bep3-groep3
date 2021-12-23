package nl.hu.bep3.order.infrastructure.rabbitmq;

import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.OrderApplication;
import nl.hu.bep3.order.application.response.OrderResponseToKitchenDTO;
import nl.hu.bep3.order.domain.Order;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;

@Component
public class QueueSender {

  private final RabbitTemplate customerTemplate;
  private final DirectExchange customerExchange;
  private final RabbitTemplate orderTemplate;
  private final DirectExchange orderExchange;

  private final Queue queue;
  
  public QueueSender(
      RabbitTemplate customerTemplate,
      @Qualifier("directCustomerExchange") DirectExchange customerExchange,
      @Qualifier("directOrderExchange") DirectExchange orderExchange,
      RabbitTemplate orderTemplate,
      @Qualifier("orderOutQueue") Queue queue) {
    this.customerTemplate = customerTemplate;
    this.customerExchange = customerExchange;
    this.orderTemplate = orderTemplate;
    this.orderExchange = orderExchange;
    this.queue = queue;
  }

  public Customer getCustomer(final UUID id) {
    String message =
        (String) customerTemplate.convertSendAndReceive(customerExchange.getName(), "customer", id);
    return OrderApplication.GSON.fromJson(message, Customer.class);
  }

  public void sendOrder(Order order)
    final String message = OrderApplication.GSON.toJson(new OrderResponseToKitchenDTO(order));
    this.orderTemplate.convertAndSend(this.queue.getName(), message);
  }
}
