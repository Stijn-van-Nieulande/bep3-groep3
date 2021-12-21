package nl.hu.bep3.order.domain;

public class Review {
    private Long reviewId;
    private String reviewMessage;
    private int rating;

    public Review(String reviewMessage, int rating) {
        this.reviewMessage = reviewMessage;
        this.rating = rating;
    }
}
