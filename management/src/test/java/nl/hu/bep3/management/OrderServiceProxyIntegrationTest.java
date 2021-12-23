package nl.hu.bep3.management;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import java.util.UUID;
import nl.hu.bep3.management.proxy.OrderServiceProxy;
import nl.hu.bep3.order.domain.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
@DisplayName("Order service integration test")
class OrderServiceProxyIntegrationTest {

  @Autowired
  @Qualifier("mockOrderService")
  private WireMockServer mockOrderService;

  @Autowired private OrderServiceProxy orderServiceProxy;

  @BeforeEach
  void setUp() throws IOException {
    ReviewMocks.setupMockGetOrderReviewsResponse(this.mockOrderService);
  }

  @Test
  @DisplayName("After getting the reviews, the reviews should be returned")
  public void whenGetReviews_thenReviewsShouldBeReturned() {
    // Make the api call.
    final Page<Review> reviews = this.orderServiceProxy.getReviews(PageRequest.of(0, 20));

    // Test if the reviews list is not empty.
    Assertions.assertFalse(reviews.getContent().isEmpty());

    // Test if the total elements is equal to the expected amount of reviews.
    Assertions.assertEquals(reviews.getTotalElements(), 2);
  }

  @Test
  @DisplayName("After getting the reviews, the correct reviews should be returned")
  public void whenGetReviews_thenTheCorrectReviewsShouldBeReturned() {
    final UUID reviewId1 = UUID.fromString("1c73df76-1809-49e5-a441-0b6ef0122139");
    final UUID reviewId2 = UUID.fromString("02e634ae-a3c5-46e6-95bd-19852f95ac3d");

    // Make the api call.
    final Page<Review> reviews = this.orderServiceProxy.getReviews(PageRequest.of(0, 20));

    // Test if the stick response ingredient list contains te expected ingredient.
    Assertions.assertTrue(
        reviews.getContent().stream()
            .allMatch(
                review ->
                    review.getReviewId().equals(reviewId1)
                        || review.getReviewId().equals(reviewId2)));
  }
}
