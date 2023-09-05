package com.techpal.sn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Autoriser les requêtes depuis ce domaine
        config.addAllowedMethod("*"); // Autoriser toutes les méthodes HTTP (GET, POST, etc.)
        config.addAllowedHeader("*"); // Autoriser tous les en-têtes

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
