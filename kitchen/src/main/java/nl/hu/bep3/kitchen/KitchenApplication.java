package nl.hu.bep3.kitchen;

import com.google.gson.Gson;
import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.kitchen")
@Import(SwaggerDataProviderConfig.class)
@EnableDiscoveryClient
@EnableFeignClients
public class KitchenApplication {

  public static final Gson GSON = new Gson();

  public static void main(final String[] args) {
    SpringApplication.run(KitchenApplication.class, args);
  }
}
