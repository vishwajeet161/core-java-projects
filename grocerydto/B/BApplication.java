package com.example.B;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title =  "Spring Boot Assignment",
				description = "Spring Boot Assignment with JPA and without DTO",
				version = "1.0.0"
		)
)
public class BApplication {

	public static void main(String[] args) {
		SpringApplication.run(BApplication.class, args);
	}

}
