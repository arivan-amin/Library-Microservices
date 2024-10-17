package com.arivanamin.library.api.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class MySecurityConfig {
    
    public static final String[] WHITE_LISTED_URLS =
        { "eureka/**", "/webjars/**", "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**",
            "/book-service/api-docs", "/patron-service/api-docs", "/borrow-service/api-docs" };
    
    private String userName = "admin";
    private String password = "admin";
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges.pathMatchers(WHITE_LISTED_URLS)
                .permitAll()
                .anyExchange()
                .authenticated())
            .httpBasic(withDefaults())
            .formLogin(withDefaults());
        return http.build();
    }
    
    @Bean
    public MapReactiveUserDetailsService userDetailsService () {
        UserDetails user = User.builder()
            .username(userName)
            .password(passwordEncoder().encode(password))
            .roles("USER")
            .build();
        return new MapReactiveUserDetailsService(user);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
