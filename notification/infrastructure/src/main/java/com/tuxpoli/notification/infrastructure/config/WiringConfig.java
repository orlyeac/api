package com.tuxpoli.notification.infrastructure.config;

import com.tuxpoli.notification.application.NotificationSendService;
import com.tuxpoli.notification.domain.EmailSender;
import com.tuxpoli.notification.domain.NotificationRepository;
import com.tuxpoli.notification.domain.EventBus;
import com.tuxpoli.notification.infrastructure.amqp.bus.EventBusAdapter;
import com.tuxpoli.notification.infrastructure.email.EmailSenderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class WiringConfig {

    @Bean
    EmailSender emailSender(JavaMailSender mailSender) {
        return new EmailSenderAdapter(mailSender);
    }

    @Bean
    EventBus eventBus() {
        return new EventBusAdapter();
    }

    @Bean
    NotificationSendService notificationSendService(
            NotificationRepository notificationRepository,
            EmailSender emailSender
    ) {
        return new NotificationSendService(
                notificationRepository,
                emailSender
        );
    }

}
