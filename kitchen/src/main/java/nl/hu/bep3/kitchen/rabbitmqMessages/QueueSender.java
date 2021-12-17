package nl.hu.bep3.kitchen.rabbitmqMessages;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
    private final RabbitTemplate dishTemplate;
    private final RabbitTemplate ingredientTemplate;
    private final DirectExchange dishExchange;
    private final DirectExchange ingredientExchange;

    public QueueSender(RabbitTemplate dishTemplate,
                       RabbitTemplate ingredientTemplate,
                       @Qualifier("directDishExchange") DirectExchange dishExchange,
                       @Qualifier("directIngredientExchange") DirectExchange ingredientExchange) {
        this.dishTemplate = dishTemplate;
        this.ingredientTemplate = ingredientTemplate;
        this.dishExchange = dishExchange;
        this.ingredientExchange = ingredientExchange;
    }

    public String getDish(final Long id){
        System.out.println("Request dish with id: " + id);
        String message = (String) dishTemplate.convertSendAndReceive(dishExchange.getName(), "dish", id);
        System.out.println("Response: " + message);
        return message;
    }

    public String getIngredient(final Long id){
        System.out.println("Request ingredient with id: " + id);
        String message = (String) ingredientTemplate.convertSendAndReceive(ingredientExchange.getName(), "ingredient", id);
        System.out.println("Response: " + message);
        return message;
    }
}
