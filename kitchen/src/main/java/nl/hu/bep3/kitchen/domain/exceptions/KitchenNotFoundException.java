package nl.hu.bep3.kitchen.domain.exceptions;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KitchenNotFoundException extends RuntimeException {
    public KitchenNotFoundException(final String message){
        super(message);
    }

    public KitchenNotFoundException(final ObjectId kitchenId){
        super(String.format("Kitchen with id \"%s\" doesnt exist.", kitchenId.toString()));
    }
}
