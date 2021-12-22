package nl.hu.bep3.kitchen.domain.exceptions;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(final String message) {
    super(message);
  }

  public OrderNotFoundException(final UUID pendingOrder) {
    super(String.format("Order with id \"%s\" doesnt exist.", pendingOrder.toString()));
  }
}
