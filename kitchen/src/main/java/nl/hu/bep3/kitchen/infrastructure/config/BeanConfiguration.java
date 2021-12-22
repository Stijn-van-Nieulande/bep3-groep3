package nl.hu.bep3.kitchen.infrastructure.config;

import nl.hu.bep3.kitchen.KitchenApplication;
import nl.hu.bep3.kitchen.domain.repository.KitchenRepository;
import nl.hu.bep3.kitchen.domain.service.DomainKitchenService;
import nl.hu.bep3.kitchen.domain.service.KitchenService;
import nl.hu.bep3.kitchen.infrastructure.rabbitmq.QueueSender;
import nl.hu.bep3.kitchen.proxy.DishServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = KitchenApplication.class)
public class BeanConfiguration {

  @Bean
  KitchenService kitchenService(final KitchenRepository KitchenRepository,
      final QueueSender queueSender, final
  DishServiceProxy proxy) {
    return new DomainKitchenService(KitchenRepository, queueSender, proxy);
  }
}
