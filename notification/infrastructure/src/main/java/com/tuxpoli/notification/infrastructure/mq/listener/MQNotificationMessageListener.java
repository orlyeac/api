package com.tuxpoli.notification.infrastructure.mq.listener;

import com.tuxpoli.common.application.NotificationSendRequest;
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
    public void listener(NotificationSendRequest notificationSendRequest) {
        notificationSendService.send(notificationSendRequest);
    }

}
