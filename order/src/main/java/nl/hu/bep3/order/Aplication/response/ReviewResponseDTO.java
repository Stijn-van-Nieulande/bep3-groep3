package nl.hu.bep3.order.Aplication.response;

import nl.hu.bep3.order.domain.Review;
import java.util.UUID;

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


