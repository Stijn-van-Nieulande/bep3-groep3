package nl.hu.bep3.order.application.response;

import java.util.UUID;
import nl.hu.bep3.order.domain.Review;

public class ReviewResponseDto {

  public UUID id;
  public int rating;
  public String reviewMessage;

  public ReviewResponseDto(final Review review) {
    this.id = review.getReviewId();
    this.rating = review.getRating();
    this.reviewMessage = review.getReviewMessage();
  }
}
