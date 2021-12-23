package nl.hu.bep3.management;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
public class WireMockConfig {
  @Bean(initMethod = "start", destroyMethod = "stop")
  public WireMockServer mockKitchenService() {
    return new WireMockServer(9561);
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  public WireMockServer mockOrderService() {
    return new WireMockServer(9562);
  }
}
