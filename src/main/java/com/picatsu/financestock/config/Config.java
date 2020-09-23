package com.picatsu.financestock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.patriques.AlphaVantageConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.timeout}")
    private int timeout;


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger doc for the Finance-picatsu project")
                        .version("0.0.1")
                        .description("Finance STOCKS API Ash project")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public AlphaVantageConnector beanAlphaApiConnector() {

        return new AlphaVantageConnector(apiKey, timeout);
    }

    @Bean
    public org.patriques.TimeSeries beanTimeSeries() {

        return new org.patriques.TimeSeries(beanAlphaApiConnector());
    }

}
