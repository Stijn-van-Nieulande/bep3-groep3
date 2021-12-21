package nl.hu.bep3.kitchen.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidKitchenException extends RuntimeException {
    public InvalidKitchenException(final String message){
        super(message);
    }

}
