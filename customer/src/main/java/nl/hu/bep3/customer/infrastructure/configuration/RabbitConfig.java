package nl.hu.bep3.customer.infrastructure.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  public Queue customerOutQueue() {
    return new Queue("bep.customer.customer", true);
  }

  @Bean
  public DirectExchange directCustomerExchange() {
    return new DirectExchange("bep.customer");
  }

  @Bean
  public Binding customerBinding(
      @Qualifier("directCustomerExchange") final DirectExchange exchange,
      @Qualifier("customerOutQueue") final Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with("customer");
  }
}
