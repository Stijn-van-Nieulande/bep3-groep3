package nl.hu.bep3.management.infrastracture.configuration;

import nl.hu.bep3.management.ManagementApplication;
import nl.hu.bep3.management.domain.repository.EmployeeRepository;
import nl.hu.bep3.management.domain.service.DomainEmployeeService;
import nl.hu.bep3.management.domain.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ManagementApplication.class)
public class BeanConfiguration {

  @Bean
  EmployeeService employeeService(final EmployeeRepository employeeRepository) {
    return new DomainEmployeeService(employeeRepository);
  }
}
