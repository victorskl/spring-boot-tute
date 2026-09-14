package hello;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenApiConfig {

    private final Environment environment;

    public OpenApiConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        String version = environment.getProperty("info.app.version");
        return new OpenAPI()
                .info(new Info()
                        .title("My REST API")
                        .version(version)
                        .description("API documentation")
                );
    }
}
