package com.java.app.ws.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class webSecurity {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeRequests(authConfig -> {
        	
        
            authConfig.antMatchers("/").permitAll();
            authConfig.antMatchers("/users/**").authenticated();
            authConfig.antMatchers("/admin/**").denyAll();
            
            })
        .formLogin(customizer.withDefaults())
        .httpBasic(customizer.withDefaults());
           
        return http.build();
        
}
}
