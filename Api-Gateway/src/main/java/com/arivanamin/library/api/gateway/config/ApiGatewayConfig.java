package com.arivanamin.library.api.gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

import static java.lang.System.getenv;

@Configuration
public class ApiGatewayConfig {
    
    public static final String EUREKA_HOST = getenv().getOrDefault("EUREKA_HOST", "localhost");
    
    public static final String EUREKA_URL = "http://%s:8761".formatted(EUREKA_HOST);
    
    @Bean
    public RouteLocator routeLocator (RouteLocatorBuilder builder) {
        return builder.routes()
            .route(getDiscoveryServerRoute())
            .route(getDiscoveryServerStaticResourcesRoute())
            .route(getBookServiceRoute())
            .route(getPatronServiceRoute())
            .route(getBorrowServiceRoute())
            .build();
    }
    
    private Function<PredicateSpec, Buildable<Route>> getDiscoveryServerRoute () {
        return r -> r.path("/eureka/web").filters(f -> f.setPath("/")).uri(EUREKA_URL);
    }
    
    private Function<PredicateSpec, Buildable<Route>> getDiscoveryServerStaticResourcesRoute () {
        return r -> r.path("/eureka/**").uri(EUREKA_URL);
    }
    
    private Function<PredicateSpec, Buildable<Route>> getBookServiceRoute () {
        return r -> r.path("/book-service/**", "/api/books/**", "/books/actuator/**")
            .uri("lb://book-service");
    }
    
    private Function<PredicateSpec, Buildable<Route>> getPatronServiceRoute () {
        return r -> r.path("/patron-service/**", "/api/patrons/**", "/patrons/actuator/**")
            .uri("lb://patron-service");
    }
    
    private Function<PredicateSpec, Buildable<Route>> getBorrowServiceRoute () {
        return r -> r.path("/borrow-service/**", "/api/borrow/**", "/api/return/**",
            "/borrows/actuator/**").uri("lb://borrow-service");
    }
}
