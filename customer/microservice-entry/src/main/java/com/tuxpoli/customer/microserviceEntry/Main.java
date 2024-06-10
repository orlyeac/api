package com.tuxpoli.customer.microserviceEntry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.tuxpoli.mq.infrastructure",
				"com.tuxpoli.axiom.infrastructure",
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
