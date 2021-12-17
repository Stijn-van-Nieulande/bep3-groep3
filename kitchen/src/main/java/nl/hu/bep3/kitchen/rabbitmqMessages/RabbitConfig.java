package nl.hu.bep3.kitchen.rabbitmqMessages;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange directDishExchange(){
        return new DirectExchange("bep.dish");
    }

    @Bean
    public DirectExchange directIngredientExchange(){
        return new DirectExchange("bep.ingredient");
    }

}
