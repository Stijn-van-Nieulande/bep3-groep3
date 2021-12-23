package nl.hu.bep3.order.domain.exception;

public class StatusInvalidException extends RuntimeException {

  public StatusInvalidException(String status) {
    super(String.format("The status \"%s\" is not supported.", status));
  }
}
