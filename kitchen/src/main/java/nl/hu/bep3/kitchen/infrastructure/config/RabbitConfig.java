package nl.hu.bep3.kitchen.infrastructure.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Bean
  public DirectExchange directDishAddExchange() {
    return new DirectExchange("Bep.dish.add");
  }

  @Bean
  public DirectExchange directDishExchange() {
    return new DirectExchange("bep.dish");
  }

  @Bean
  public DirectExchange directIngredientExchange() {
    return new DirectExchange("bep.ingredient");
  }

  @Bean
  public DirectExchange directMenuExchange() {
    return new DirectExchange("bep.menu");
  }

}
