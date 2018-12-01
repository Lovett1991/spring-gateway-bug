package com.example.springgateway;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayITConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p
                    .method(GET)
                .uri(format("http://localhost:%d", GatewayIT.getStubServicePort())))
            .build();
    }
    
}
