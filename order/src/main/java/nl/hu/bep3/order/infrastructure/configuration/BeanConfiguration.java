package nl.hu.bep3.order.infrastructure.configuration;

import nl.hu.bep3.order.domain.repository.OrderRepository;
import nl.hu.bep3.order.domain.service.DomainOrderService;
import nl.hu.bep3.order.OrderApplication;
import nl.hu.bep3.order.domain.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = OrderApplication.class)
public class BeanConfiguration {

  @Bean
  OrderService orderService(final OrderRepository orderRepository) {
    return new DomainOrderService(orderRepository);
  }
}