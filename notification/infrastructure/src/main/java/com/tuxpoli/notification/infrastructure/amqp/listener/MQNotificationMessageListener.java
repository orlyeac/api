package com.tuxpoli.notification.infrastructure.amqp.listener;

import com.tuxpoli.notification.application.NotificationSendRequest;
import com.tuxpoli.notification.application.NotificationSendService;
import com.tuxpoli.notification.domain.model.Notification;
import com.tuxpoli.notification.domain.model.NotificationKind;
import org.springframework.stereotype.Component;

@Component
public class MQNotificationMessageListener {

    private final NotificationSendService notificationSendService;

    public MQNotificationMessageListener(NotificationSendService notificationSendService) {
        this.notificationSendService = notificationSendService;
    }

    public void listener(Notification notificationSendRequest) {
        notificationSendService.send(
                new NotificationSendRequest(
                        1L,
                        "johndoe@email.com",
                        "John Doe",
                        NotificationKind.WELCOME
                )
        );
    }

}
