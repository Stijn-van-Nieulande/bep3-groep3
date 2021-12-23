package nl.hu.bep3.dish.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIngredientException extends RuntimeException {

  public InvalidIngredientException(String message) {
    super(message);
  }
}
