package nl.hu.bep3.order.Aplication.response;

import nl.hu.bep3.order.domain.Review;
import java.util.UUID;

public class ReviewResponseDTO {
    private UUID id;
    public int rating;
    public String message;

  public ReviewResponseDTO(int rating, String message) {
    this.rating = rating;
    this.message = message;
  }

  public ReviewResponseDTO(Review review) {
    this.id = review.getReviewId();
    this.rating = review.getRating();
    this.message = review.getReviewMessage();
  }

  public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
