package nl.hu.bep3.dish.infrastructure.config;

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
  public Queue dishOutQueue() {
    return new Queue("bep.dish.dish", true);
  }

  @Bean
  public DirectExchange directDishExchange(){
    return new DirectExchange("bep.dish");
  }


  @Bean
  public Binding binding(@Qualifier("directDishExchange") DirectExchange exchange, @Qualifier("dishOutQueue") Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with("dish");
  }

  @Bean
  public Queue ingredientOutQueue() {
    return new Queue("bep.dish.ingredient", true);
  }

  @Bean
  public DirectExchange directIngredientExchange(){
    return new DirectExchange("bep.ingredient");
  }

  @Bean
  public Binding ingredientBinding(@Qualifier("directIngredientExchange") DirectExchange exchange, @Qualifier("ingredientOutQueue") Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with("ingredient");
  }

  @Bean
  public Queue menuOutQueue(){
    return new Queue("bep.dish.menu", true);
  }

  @Bean
  public DirectExchange directMenuExchange(){
    return new DirectExchange("bep.menu");
  }

  @Bean
  public Binding menuBinding(@Qualifier("directMenuExchange") DirectExchange exchange, @Qualifier("menuOutQueue") Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with("ingredient");
  }
}
