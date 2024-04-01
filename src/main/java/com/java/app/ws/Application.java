package com.java.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages={"com.java.app", "com.java.app.ws.controllers","com.java.app.ws.postServices", "com.java.app.ws.services"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return  new BCryptPasswordEncoder();
}
}



localhost:8080/project_manager/




/prpject/delete/450 => supprimer
/projet/show/450 => afficher les détails du projet dont l'id est 450