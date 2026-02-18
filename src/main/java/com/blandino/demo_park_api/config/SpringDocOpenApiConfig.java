package com.blandino.demo_park_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest Api Spring Park")
                                .description("Api Feita Para Gestao de estacionaento")
                                .version("V1")
                                .license(new License().name("Apache 2,o").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Blandino Mucumbe").email("mucumbeblandino@gmail.com"))
                );
    }
}
