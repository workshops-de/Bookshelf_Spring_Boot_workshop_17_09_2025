package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("!prod")
//@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {

  @Bean
  public OpenAPI api(SwaggerProperties swaggerProperties) {
    return new OpenAPI()
        .info(
            new Info()
                .title(swaggerProperties.title())
                .version(swaggerProperties.version())
                .description("This bookshelf has a capacity of %d books.".formatted(swaggerProperties.capacity()))
                .license(new License()
                    .name(swaggerProperties.license().name())
                    .url(swaggerProperties.license().url().toString())
                )
        );
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
      }
    };
  }
}
