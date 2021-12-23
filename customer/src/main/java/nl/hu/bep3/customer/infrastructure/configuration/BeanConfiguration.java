package nl.hu.bep3.customer.infrastructure.configuration;

import nl.hu.bep3.customer.CustomerApplication;
import nl.hu.bep3.customer.domain.repository.CustomerRepository;
import nl.hu.bep3.customer.domain.service.CustomerService;
import nl.hu.bep3.customer.domain.service.DomainCustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CustomerApplication.class)
public class BeanConfiguration {

  @Bean
  CustomerService customerService(final CustomerRepository customerRepository) {
    return new DomainCustomerService(customerRepository);
  }
}
