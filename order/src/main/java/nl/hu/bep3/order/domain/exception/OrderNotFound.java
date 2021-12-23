package nl.hu.bep3.order.domain.exception;

import java.util.UUID;

public class OrderNotFound extends RuntimeException {

  public OrderNotFound(final UUID id) {
    super("Could not find order by id " + id);
  }
}
