package com.example.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoyageurMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoyageurMockApplication.class, args);
		System.out.println("🚀 Voyageur Mock Backend is running!");
		System.out.println("📖 API Documentation: http://localhost:8080/swagger-ui.html");
	}
}