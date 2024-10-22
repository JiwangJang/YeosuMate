package com.practice.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class AuthConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Autentication required path setting
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("auth/register", "auth/login").permitAll()
                        .anyRequest().authenticated());

        return httpSecurity.build();
    }
}
