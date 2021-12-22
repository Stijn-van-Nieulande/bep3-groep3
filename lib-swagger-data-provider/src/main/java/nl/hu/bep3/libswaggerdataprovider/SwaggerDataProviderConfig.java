package nl.hu.bep3.libswaggerdataprovider;

import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SwaggerDataProviderConfig {

  static {
    SpringDocUtils.getConfig()
        .replaceParameterObjectWithClass(
            org.springframework.data.domain.Pageable.class, Pageable.class)
        .replaceParameterObjectWithClass(
            org.springframework.data.domain.PageRequest.class, Pageable.class);
  }

  @Value("${app.swagger.endpoint}")
  private String swaggerEndpoint;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    System.out.println(this.swaggerEndpoint);
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(SwaggerDataProviderConfig.this.swaggerEndpoint);
      }
    };
  }
}
