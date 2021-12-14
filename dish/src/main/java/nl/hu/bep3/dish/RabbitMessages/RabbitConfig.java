package nl.hu.bep3.dish.RabbitMessages;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
  @Bean
  public Queue dishOutQueue() {
    return new Queue("bep.dish.requests", true);
  }

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange("bep.dish");
  }

  @Bean
  public Binding binding(DirectExchange exchange, Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with("dish");
  }
}
