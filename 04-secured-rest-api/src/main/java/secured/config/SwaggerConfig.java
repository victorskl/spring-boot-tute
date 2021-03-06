package secured.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private final Environment environment;

  @Autowired
  public SwaggerConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
//                .apis(RequestHandlerSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("secured.endpoint"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    String version = environment.getProperty("info.app.version");
    return new ApiInfo(
        "My REST API",
        "Some custom description of API.",
        version,
        "Terms of service",
        new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
        "License of API", "API license URL", Collections.emptyList());
  }
}
