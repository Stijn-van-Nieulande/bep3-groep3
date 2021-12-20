package nl.hu.bep3.libswaggerdataprovider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerDataProviderConfig {
  @Value("${app.swagger.endpoint}")
  private String swaggerEndpoint;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    System.out.println(this.swaggerEndpoint);
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry
            .addMapping("/v3/api-docs")
            .allowedOrigins(SwaggerDataProviderConfig.this.swaggerEndpoint);
      }
    };
  }
}
