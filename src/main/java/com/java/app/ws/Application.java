package com.java.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages={"com.java.app", "com.java.app.ws.controller", "com.java.app.ws.service"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return  new BCryptPasswordEncoder();
}
}
