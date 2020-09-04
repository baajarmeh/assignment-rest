package io.baay.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig to provide automated API documentation with simple use of annotations. URL to view
 * api documentation in json format: http://<HOST>:<PORT>/v2/api-docs URL to view api documentation
 * in rich UI: http://<HOST>:<PORT>/swagger-ui.html
 */
@Profile("local")
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("io.baay.assignment.controllers"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Mobile Device Details Service")
        .description(
            "Service helps consumers to fetch Mobile handset details with the allowed filters")
        .version("1.0")
        .contact(contact())
        .build();
  }

  private Contact contact() {
    return new Contact("Vish", "https://github.com/vishuu1101", "battavishwanath@gmail.com");
  }
}
