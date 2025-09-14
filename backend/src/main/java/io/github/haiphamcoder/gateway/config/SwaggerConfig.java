package io.github.haiphamcoder.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway Management")
                        .version("0.0.1")
                        .description("API Gateway Management System with Kong Gateway")
                        .contact(new Contact()
                                .name("Hai Pham Ngoc")
                                .email("ngochai285nd@gmail.com")
                                .url("https://github.com/haiphamcoder"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Development Server"))
                .externalDocs(new ExternalDocumentation()
                        .description("API Gateway Management GitHub Repository")
                        .url("https://github.com/haiphamcoder/api-gateway-management"));
    }

}
