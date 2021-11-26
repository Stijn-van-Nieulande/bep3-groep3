package nl.hu.bep3.customer.domain;

public class CustomerId {
    private Long id;

    public CustomerId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
