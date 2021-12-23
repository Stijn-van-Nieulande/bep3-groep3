package nl.hu.bep3.management.proxy;

import nl.hu.bep3.order.domain.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", url = "${app.feign.order.url}")
public interface OrderServiceProxy {

  @GetMapping("/order/review")
  Page<Review> getReviews(Pageable pageable);
}
