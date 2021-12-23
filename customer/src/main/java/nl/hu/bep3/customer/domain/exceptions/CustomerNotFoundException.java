package nl.hu.bep3.customer.domain.exceptions;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(final String message) {
    super(message);
  }

  public CustomerNotFoundException(final UUID id) {
    super(String.format("Customer with id \"%s\" does not exist.", id.toString()));
  }
}
