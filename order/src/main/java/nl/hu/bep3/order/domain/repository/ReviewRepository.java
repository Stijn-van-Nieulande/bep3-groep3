package nl.hu.bep3.order.domain.repository;

import nl.hu.bep3.order.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {

  Page<Review> findAllPaginated(Pageable pageable);

  Review save(Review review);
}
