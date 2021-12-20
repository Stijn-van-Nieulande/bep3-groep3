package nl.hu.bep3.management;

import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.Role;
import nl.hu.bep3.management.domain.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.management")
@EnableFeignClients("nl.hu.bep3.management.proxy")
public class ManagementApplication {
  @Value("${app.swagger.endpoint}")
  private String swaggerEndpoint;

  public static void main(final String[] args) {
    SpringApplication.run(ManagementApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(final EmployeeService service) {
    return (args) -> {
      final Employee employee =
          service.createEmployee(
              new Employee(
                  "Yeet",
                  "Delete",
                  0D,
                  Role.THE_PERSON_BEHIND_THE_DESK_THAT_ACTUALLY_DOESNT_WORK_HERE));

      System.out.println(employee.getId());
    };
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    System.out.println(ManagementApplication.this.swaggerEndpoint);
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry
            .addMapping("/v3/api-docs")
            .allowedOrigins(ManagementApplication.this.swaggerEndpoint);
      }
    };
  }
}
