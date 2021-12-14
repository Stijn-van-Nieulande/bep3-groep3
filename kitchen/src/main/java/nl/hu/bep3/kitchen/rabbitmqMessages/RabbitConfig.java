package nl.hu.bep3.kitchen.rabbitmqMessages;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"Kitchen"})
@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("bep.dish");
    }

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("kitchen.fanout");
    }

    @Profile("receiver")
    private static class ReceiverConfig {


        @Bean
        public QueueReceiver receiver() {
            return new QueueReceiver();
        }
    }

}
