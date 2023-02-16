package com.bank.profile.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Это класс конфигурации для Swagger, инструмента для создания документации по API.
 * Он используется для предоставления пользовательской конфигурации для Swagger в приложении Spring Boot.
 * <p>
 * Класс снабжен аннотацией, @Configurationуказывающей, что это класс конфигурации,
 * и @EnableSwagger2для включения поддержки Swagger в приложении.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("http://http://localhost:8089/api/profile/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Bank API")
                .version("1.0.0")
                .contact(new Contact()
                        .email("me@upagge.ru")
                        .url("https://uPagge.ru")
                        .name("Sazonov Vladimir")
                )
        );
    }
}
