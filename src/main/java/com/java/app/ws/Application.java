package com.java.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()

@ComponentScan(basePackages = { "com.java.app.ws.controller", "com.java.app.ws.service", "com.java.app.ws.service.Impl", "com.java.app.ws.security" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return  new BCryptPasswordEncoder();
}
}
