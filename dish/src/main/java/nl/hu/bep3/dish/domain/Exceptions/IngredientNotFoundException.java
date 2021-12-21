package nl.hu.bep3.dish.domain.Exceptions;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotFoundException extends RuntimeException {
  public IngredientNotFoundException(String message) {
    super(message);
  }

  public IngredientNotFoundException(final UUID id){
    super(String.format("Ingredient with id \"%s\" doesnt exist.", id.toString()));
  }
}
