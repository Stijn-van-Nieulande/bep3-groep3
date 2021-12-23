package nl.hu.bep3.management;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.io.IOException;
import nl.hu.bep3.management.util.MockUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReviewMocks {

  public static void setupMockGetOrderReviewsResponse(final WireMockServer mockService)
      throws IOException {
    mockService.stubFor(
        WireMock.get(WireMock.urlPathEqualTo("/order/review"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(MockUtils.loadPayload("payload/order-get-reviews-response.json"))));
  }
}
