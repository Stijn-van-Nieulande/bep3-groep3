package nl.hu.bep3.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.customer")
public class CustomerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(CustomerApplication.class, args);
  }
}
