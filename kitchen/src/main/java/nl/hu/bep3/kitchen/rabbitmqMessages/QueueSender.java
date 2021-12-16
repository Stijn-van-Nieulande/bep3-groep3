package nl.hu.bep3.kitchen.rabbitmqMessages;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    private final RabbitTemplate template;
    private final DirectExchange exchange;

    public QueueSender(RabbitTemplate template, DirectExchange exchange){
        this.template = template;
        this.exchange = exchange;
    }

    public String getDish(final Long id){
        System.out.println("Send request with id: " + id);
        String message = (String) template.convertSendAndReceive(exchange.getName(), "dish", id);
        System.out.println("Response: " + message);
        return message;
    }
}
