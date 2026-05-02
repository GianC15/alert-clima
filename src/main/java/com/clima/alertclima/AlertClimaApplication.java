package com.clima.alertclima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching  // habilita el sistema de caché de Spring
@EnableScheduling
public class AlertClimaApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlertClimaApplication.class, args);
	}
}