package com.tuxpoli.customer.microserviceEntry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.tuxpoli.customer.domain",
				"com.tuxpoli.customer.application",
				"com.tuxpoli.customer.infrastructure"
		}
)
@EnableJpaRepositories(
		basePackages = {
				"com.tuxpoli.customer.infrastructure"
		}
)
@EntityScan(
		basePackages = "com.tuxpoli.customer.infrastructure"
)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
