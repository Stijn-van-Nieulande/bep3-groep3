package nl.hu.bep3.order.domain;

import java.util.UUID;
import nl.hu.bep3.order.infrastructure.repository.Persistable;

public class Review implements Persistable<UUID> {

  private final String reviewMessage;
  private final int rating;
  private UUID reviewId;

  public Review(final String reviewMessage, final int rating) {
    this.reviewMessage = reviewMessage;
    this.rating = rating;
  }

  public UUID getReviewId() {
    return this.reviewId;
  }

  public String getReviewMessage() {
    return this.reviewMessage;
  }

  public int getRating() {
    return this.rating;
  }

  @Override
  public UUID getId() {
    return this.reviewId;
  }

  @Override
  public void setId(final UUID uuid) {
    this.reviewId = uuid;
  }

  @Override
  public boolean isNew() {
    return this.reviewId == null;
  }
}
