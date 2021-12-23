package nl.hu.bep3.order.domain;

import java.util.UUID;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class Review implements Persistable<UUID> {

  private UUID reviewId;
  private String reviewMessage;
  private int rating;

  public Review(String reviewMessage, int rating) {
    this.reviewMessage = reviewMessage;
    this.rating = rating;
  }

  public UUID getReviewId() {
    return reviewId;
  }

  public String getReviewMessage() {
    return reviewMessage;
  }

  public int getRating() {
    return rating;
  }

  @Override
  public UUID getId() {
    return reviewId;
  }

  @Override
  public void setId(UUID uuid) {
    this.reviewId = uuid;
  }

  @Override
  public boolean isNew() {
    return this.reviewId == null;
  }
}
