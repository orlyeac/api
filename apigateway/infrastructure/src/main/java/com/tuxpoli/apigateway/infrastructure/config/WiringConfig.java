package com.tuxpoli.apigateway.infrastructure.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiringConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route
                        .path(
                                "/api/v1/customers/**",
                                "/api/v1/auth/**")
                        .uri("lb://customer")
                )
                .route(route -> route
                        .path(
                                "/api/v1/reviews/**"
                        )
                        .uri("lb://review")
                )
                .build();

    }
}
