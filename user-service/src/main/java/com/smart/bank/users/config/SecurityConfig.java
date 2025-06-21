package com.smart.bank.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable()
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ) // Disable CSRF for stateless API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/users/register",
                                "/api/v1/users/username/**",
                                "/actuator/**",
                                "/h2-console/**",// Allow actuator endpoints
                                "/v3/api-docs/**",       // OpenAPI/Swagger
                                "/swagger-ui/**",         // Swagger UI
                                "/swagger-resources/**"   // Swagger resources
                        ).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")// Public endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .httpBasic(httpBasic -> {}); // Optional: Enable basic auth if needed

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
