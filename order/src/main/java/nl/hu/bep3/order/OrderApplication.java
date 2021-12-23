package nl.hu.bep3.order;

import com.google.gson.Gson;
import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.order")
@Import(SwaggerDataProviderConfig.class)
@EnableFeignClients
public class OrderApplication {

  public static final Gson GSON = new Gson();

  public static void main(final String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }
}
