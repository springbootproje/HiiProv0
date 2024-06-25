package com.java.app.ws.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    //: Interface pour obtenir les configurations CORS à partir de différentes sources.
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");// Adjust to your front-end URL
        //Autorise tous les en-têtes HTTP
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        //: Indique si les informations d'authentification sont autorisee
        configuration.setAllowCredentials(true);
// utilisée pour appliquer la configuration CORS à des URL spécifiques
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //Enregistre la configuration CORS pour toutes les routes
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}