package nl.hu.bep3.customer.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(final String message){
        super(message);
    }

    public CustomerNotFoundException(final Long id){
        super(String.format("Customer with id \"%s\" doesn't exist.", id.toString()));
    }
}
