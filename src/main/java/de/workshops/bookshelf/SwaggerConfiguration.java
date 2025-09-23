package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
}
