package nl.hu.bep3.dish.RabbitMessages;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
  @Bean
  public Queue dishOutQueue() {
    return new Queue("dishOut", true);
  }
  @Bean
  public Queue dishInQueue() {
    return new Queue("dishIn", true);
  }
}
