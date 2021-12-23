package nl.hu.bep3.management;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.io.IOException;
import nl.hu.bep3.management.util.MockUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class KitchenMocks {

  public static void setupMockGetKitchenStockResponse(final WireMockServer mockService)
      throws IOException {
    mockService.stubFor(
        WireMock.get(
                WireMock.urlPathMatching(
                    "(?i)^\\/kitchen/stock\\/(?:[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12})$"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(MockUtils.loadPayload("payload/kitchen-get-stock-response.json"))));
  }

  public static void setupMockPatchKitchenStockResponse(final WireMockServer mockService)
      throws IOException {
    mockService.stubFor(
        WireMock.patch(
                WireMock.urlPathMatching(
                    "(?i)^\\/kitchen/stock\\/(?:[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12})\\/(?:[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12})$"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(MockUtils.loadPayload("payload/kitchen-patch-stock-response.json"))));
  }

  public static void setupMockGetKitchenMenuResponse(final WireMockServer mockService)
      throws IOException {
    mockService.stubFor(
        WireMock.get(
                WireMock.urlPathMatching(
                    "(?i)^\\/kitchen/menu\\/(?:[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12})$"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(MockUtils.loadPayload("payload/kitchen-get-menu-response.json"))));
  }
}
