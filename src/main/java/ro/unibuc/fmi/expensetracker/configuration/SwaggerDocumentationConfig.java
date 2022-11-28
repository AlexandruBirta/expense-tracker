package ro.unibuc.fmi.expensetracker.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info().title("Expense Tracker API")
                                .description("Expense tracker assignment project for Software System Modeling")
                                .version("v0.0.1")
                );

    }

}