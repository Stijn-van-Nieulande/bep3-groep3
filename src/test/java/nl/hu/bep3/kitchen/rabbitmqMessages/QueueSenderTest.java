package nl.hu.bep3.kitchen.rabbitmqMessages;

import com.google.gson.Gson;
import nl.hu.bep3.kitchen.application.KitchenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;


import static org.junit.jupiter.api.Assertions.*;

class QueueSenderTest {
        @Mock
        private KitchenService queueSender;
        @Test
        public void sugon() {
                queueSender.getDish();
        }

}