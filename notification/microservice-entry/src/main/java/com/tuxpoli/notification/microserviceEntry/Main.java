package com.tuxpoli.notification.microserviceEntry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.tuxpoli.common.domain",
				"com.tuxpoli.notification.domain",
				"com.tuxpoli.notification.application",
				"com.tuxpoli.mq.infrastructure",
				"com.tuxpoli.notification.infrastructure"
		}
)
@EnableJpaRepositories(
		basePackages = {
				"com.tuxpoli.notification.infrastructure"
		}
)
@EntityScan(
		basePackages = "com.tuxpoli.notification.infrastructure"
)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
