package nl.hu.bep3.order.domain;

public class Payment {
    private Long paymentId;
    private boolean payed;
    private float price;

    public Payment(boolean payed, float price) {
        this.payed = payed;
        this.price = price;
    }

    public boolean charching(){
        return true;

    }
}
