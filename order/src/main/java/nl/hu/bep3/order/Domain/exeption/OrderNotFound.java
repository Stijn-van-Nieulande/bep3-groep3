package nl.hu.bep3.order.domain.exeption;

import java.util.UUID;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(UUID id) {
        super("Could not find order by id " + id);
    }
}