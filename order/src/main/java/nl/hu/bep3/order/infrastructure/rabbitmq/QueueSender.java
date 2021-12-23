package nl.hu.bep3.order.infrastructure.rabbitmq;

import java.util.UUID;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.order.OrderApplication;
import nl.hu.bep3.order.application.response.OrderResponseToKitchenDto;
import nl.hu.bep3.order.domain.Order;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

  private final RabbitTemplate customerTemplate;
  private final DirectExchange customerExchange;
  private final RabbitTemplate orderTemplate;

  private final Queue queue;

  public QueueSender(
      final RabbitTemplate customerTemplate,
      @Qualifier("directCustomerExchange") final DirectExchange customerExchange,
      final RabbitTemplate orderTemplate,
      @Qualifier("orderOutQueue") final Queue queue) {
    this.customerTemplate = customerTemplate;
    this.customerExchange = customerExchange;
    this.orderTemplate = orderTemplate;
    this.queue = queue;
  }

  public Customer getCustomer(final UUID id) {
    final String message =
        (String)
            this.customerTemplate.convertSendAndReceive(
                this.customerExchange.getName(), "customer", id);
    return OrderApplication.GSON.fromJson(message, Customer.class);
  }

  public void sendOrder(final Order order) {
    final String message = OrderApplication.GSON.toJson(new OrderResponseToKitchenDto(order));
    this.orderTemplate.convertAndSend(this.queue.getName(), message);
  }
}
