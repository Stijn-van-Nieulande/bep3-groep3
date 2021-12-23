package nl.hu.bep3.management;

import nl.hu.bep3.libswaggerdataprovider.SwaggerDataProviderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "nl.hu.bep3.management")
@EnableFeignClients("nl.hu.bep3.management.proxy")
@Import(SwaggerDataProviderConfig.class)
public class ManagementApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ManagementApplication.class, args);
  }
}
