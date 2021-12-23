package nl.hu.bep3.order.infrastructure.repository.mongo;

import nl.hu.bep3.order.domain.Review;
import nl.hu.bep3.order.domain.repository.ReviewRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoReviewRepository implements ReviewRepository {

  private final SpringDataMongoReviewRepository reviewRepository;

  public MongoReviewRepository(final SpringDataMongoReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public Page<Review> findAllPaginated(final Pageable pageable) {
    return this.reviewRepository.findAll(pageable);
  }

  @Override
  public Review save(Review review) {
    return this.reviewRepository.save(review);
  }
}
