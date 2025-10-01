package br.com.hospidata.history_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hospidataHistoryApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hospidata - History Service API")
                        .description("API responsável pelo gerenciamento de históricos médicos.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://github.com/Hospidata-techchallange/HospiData"))
                );
    }
}