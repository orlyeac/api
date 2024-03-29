package com.tuxpoli.notification.application;

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

    public void send(NotificationSendRequest notificationSendRequest) {
        Notification notification = Notification.create(
                notificationSendRequest.toId(),
                notificationSendRequest.toEmail(),
                notificationSendRequest.toName(),
                notificationSendRequest.kind(),
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
