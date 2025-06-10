package com.jobboard.jobboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI jobBoardOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                                .title("JobBoard API")
                                .version("v1.0")
                                .description("A simple job board service with JWT auth, job CRUD, applications, etc.")
                                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                        // you can also add .contact(new Contact()...)
                );
    }
}
