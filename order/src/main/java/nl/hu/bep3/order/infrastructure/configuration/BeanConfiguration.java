package nl.hu.bep3.order.infrastructure.configuration;

import nl.hu.bep3.order.Domain.repository.OrderRepository;
import nl.hu.bep3.order.Domain.service.DomainOrderService;
import nl.hu.bep3.order.OrderApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = OrderApplication.class)
public class BeanConfiguration {

    @Bean
    DomainOrderService orderService(final OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }