package nl.hu.bep3.order.Domain.exeption;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(Long id) {
        super("Could not find order by id " + id);
    }
}