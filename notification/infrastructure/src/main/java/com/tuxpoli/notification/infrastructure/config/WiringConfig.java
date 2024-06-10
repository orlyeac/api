package com.tuxpoli.notification.infrastructure.config;

import com.tuxpoli.common.domain.EventBus;
import com.tuxpoli.mq.infrastructure.EventBusAMQPAdapter;
import com.tuxpoli.notification.application.NotificationSendService;
import com.tuxpoli.notification.domain.EmailSender;
import com.tuxpoli.notification.domain.NotificationRepository;
import com.tuxpoli.notification.infrastructure.email.EmailSenderAdapter;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class WiringConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.customer-created}")
    private String internalCustomerCreatedRoutingKey;

    @Bean
    EmailSender emailSender(JavaMailSender mailSender) {
        return new EmailSenderAdapter(mailSender);
    }

    @Bean
    EventBus eventBus(AmqpTemplate amqpTemplate) {
        return new EventBusAMQPAdapter(amqpTemplate);
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

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.notificationQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalCustomerCreatedRoutingKey);
    }

}