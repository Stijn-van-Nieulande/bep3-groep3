package nl.hu.bep3.order;

import com.google.gson.Gson;
import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.order")
@Import(SwaggerDataProviderConfig.class)
public class OrderApplication {

  public static final Gson GSON = new Gson();

  public static void main(final String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }


}
