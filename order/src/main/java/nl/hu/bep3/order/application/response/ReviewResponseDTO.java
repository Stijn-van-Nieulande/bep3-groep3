package nl.hu.bep3.order.application.response;

import java.util.UUID;
import nl.hu.bep3.order.domain.Review;

public class ReviewResponseDTO {

  public UUID id;
  public int rating;
  public String reviewMessage;

  public ReviewResponseDTO(int rating, String reviewMessage) {
    this.rating = rating;
    this.reviewMessage = reviewMessage;
  }

  public ReviewResponseDTO(Review review) {
    this.id = review.getReviewId();
    this.rating = review.getRating();
    this.reviewMessage = review.getReviewMessage();
  }
}
