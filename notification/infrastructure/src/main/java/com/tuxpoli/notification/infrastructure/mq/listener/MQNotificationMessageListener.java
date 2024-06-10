package com.tuxpoli.notification.infrastructure.mq.listener;

import com.tuxpoli.common.domain.CustomerCreatedEvent;
import com.tuxpoli.notification.application.NotificationSendService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQNotificationMessageListener {

    private final NotificationSendService notificationSendService;

    public MQNotificationMessageListener(NotificationSendService notificationSendService) {
        this.notificationSendService = notificationSendService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void listener(CustomerCreatedEvent customerCreatedEvent) {
        notificationSendService.send(customerCreatedEvent);
    }


}
