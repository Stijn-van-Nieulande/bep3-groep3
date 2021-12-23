package nl.hu.bep3.management.application.rest;

import nl.hu.bep3.management.proxy.OrderServiceProxy;
import nl.hu.bep3.order.domain.Review;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
  private final OrderServiceProxy orderServiceProxy;

  public ReviewController(final OrderServiceProxy orderServiceProxy) {
    this.orderServiceProxy = orderServiceProxy;
  }

  @GetMapping()
  public Page<Review> getReviews(@ParameterObject final Pageable pageable) {
    return this.orderServiceProxy.getReviews(pageable);
  }
}
