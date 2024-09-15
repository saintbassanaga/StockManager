package tech.saintbassanaga.stockmanager.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Stock Management API",
                version = "1.0",
                description = "The Stock Manager project is a sophisticated web-based application " +
                        "designed to streamline inventory management for businesses of all sizes. " +
                        "It provides an intuitive platform for tracking stock levels,\n" +
                        " managing inventory, and generating insightful reports, " +
                        "facilitating efficient operations and informed decision-making.",
                contact = @Contact(
                        name = "Saint Paul Bassanaga",
                        url = "www.saintbassanaga.tech",
                        email = "saintbassanaga01@icloud.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://springdoc.org"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server"),
                @Server(url = "https://api.stockman.com", description = "Production server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    // No need for @Bean in this case since @OpenAPIDefinition is used.
}
