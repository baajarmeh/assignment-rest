package io.baay.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@EnableSwagger2
public class AssignmentRestApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AssignmentRestApplication.class, args);
	}
	
	@Bean
	public Docket mobileApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("io.baay.assignment"))
			.paths(PathSelectors.any())
			.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Assignment Handset Service")
			.description(
				"Service helps consumers to fetch Mobile handset details with the allowed filters")
			.version("1.0")
			.contact(contact())
			.build();
	}
	
	private Contact contact() {
		return new Contact("Basel", "https://github.com/baajarmeh", "ajjurtech@gmail.com");
	}
}
