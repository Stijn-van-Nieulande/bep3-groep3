package nl.hu.bep3.order.infrastructure.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  public DirectExchange directCustomerExchange() {
    return new DirectExchange("bep.customer");
  }

  @Bean
  public DirectExchange directOrderExchange() {
    return new DirectExchange("bep.Order");
  }
}
