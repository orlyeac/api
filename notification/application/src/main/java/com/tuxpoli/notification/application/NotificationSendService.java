package com.tuxpoli.notification.application;

import com.tuxpoli.common.domain.CustomerCreatedEvent;
import com.tuxpoli.notification.domain.model.NotificationKind;
import com.tuxpoli.notification.domain.*;
import com.tuxpoli.notification.domain.model.Notification;

import java.time.LocalDateTime;

public class NotificationSendService {

    private final NotificationRepository notificationRepository;

    private final EmailSender emailSender;

    public NotificationSendService(
            NotificationRepository notificationRepository,
            EmailSender emailSender
    ) {
        this.notificationRepository = notificationRepository;
        this.emailSender = emailSender;
    }

    public void send(CustomerCreatedEvent customerCreatedEvent) {
        Notification notification = Notification.create(
                customerCreatedEvent.id(),
                customerCreatedEvent.email(),
                customerCreatedEvent.name(),
                NotificationKind.WELCOME,
                LocalDateTime.now()
        );
        notificationRepository.save(notification);
        try {
            emailSender.sendEmail(
                    notification.getToEmail(),
                    notification.getKind().getSubject(),
                    notification.getKind().getMessage()
                            .formatted(notification.getToName())
            );
        }
        catch (Exception e) {
            System.out.println("email failed");
            // publish event
        }
    }
}
