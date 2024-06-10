package com.tuxpoli.review.microserviceEntry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.tuxpoli.axiom.infrastructure",
				"com.tuxpoli.review.infrastructure"
		}
)
@EnableJpaRepositories(
		basePackages = {
				"com.tuxpoli.review.infrastructure"
		}
)
@EntityScan(
		basePackages = "com.tuxpoli.review.infrastructure"
)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
