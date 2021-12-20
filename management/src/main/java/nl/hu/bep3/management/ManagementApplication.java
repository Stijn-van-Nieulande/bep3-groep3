package nl.hu.bep3.management;

import nl.hu.bep3.management.domain.Employee;
import nl.hu.bep3.management.domain.Role;
import nl.hu.bep3.management.domain.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.management")
@EnableFeignClients("nl.hu.bep3.management.proxy")
public class ManagementApplication {
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
}
