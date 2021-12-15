package nl.hu.bep3.order.Domain;

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
