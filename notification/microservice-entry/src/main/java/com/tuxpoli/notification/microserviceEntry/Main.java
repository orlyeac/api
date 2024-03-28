package com.tuxpoli.notification.microserviceEntry;

import com.tuxpoli.notification.application.NotificationSendRequest;
import com.tuxpoli.notification.application.NotificationSendService;
import com.tuxpoli.notification.domain.model.NotificationKind;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.tuxpoli.notification.infrastructure",
				"com.tuxpoli.notification.application",
				"com.tuxpoli.notification.domain"
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
