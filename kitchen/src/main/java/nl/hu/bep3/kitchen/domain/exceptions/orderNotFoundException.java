package nl.hu.bep3.kitchen.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class orderNotFoundException extends RuntimeException{
    public orderNotFoundException(final String message){
        super(message);
    }

    public orderNotFoundException(final Long pendingOrder){
        super(String.format("Order with id \"%s\" doesnt exist.", pendingOrder.toString()));
    }
}
