package nl.hu.bep3.customer.infrastructure.rabbitmq;

import java.util.Optional;
import java.util.UUID;
import nl.hu.bep3.customer.CustomerApplication;
import nl.hu.bep3.customer.application.response.CustomerOutDto;
import nl.hu.bep3.customer.domain.Customer;
import nl.hu.bep3.customer.domain.service.CustomerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class QueueReceiver {

  private final CustomerService customerService;

  public QueueReceiver(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RabbitListener(queues = "bep.customer.customer")
  public String getCustomer(final UUID id) {
    System.out.println("customer: " + id);
    Optional<Customer> optionalCustomer = this.customerService.findCustomer(id);
    if (optionalCustomer.isPresent()) {
      CustomerOutDto customer = new CustomerOutDto(optionalCustomer.get());
      return CustomerApplication.GSON.toJson(customer);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }
}
