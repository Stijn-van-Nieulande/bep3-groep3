package nl.hu.bep3.dish.domain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DishNotFoundException extends RuntimeException {
  public DishNotFoundException(String message) {
    super(message);
  }

  public DishNotFoundException(final UUID id){
    super(String.format("Dish with id \"%s\" doesnt exist.", id.toString()));
  }
}
