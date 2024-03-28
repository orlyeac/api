package com.tuxpoli.notification.infrastructure.mapper;

import com.tuxpoli.notification.domain.model.Notification;
import com.tuxpoli.notification.infrastructure.persistence.jpa.NotificationJPAEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NotificationToNotificationJPAEntityMapper implements Function<Notification, NotificationJPAEntity> {

    public NotificationJPAEntity apply(Notification notification) {
        return new NotificationJPAEntity(
                notification.getId(),
                notification.getToId(),
                notification.getToEmail(),
                notification.getToName(),
                notification.getKind(),
                notification.getCreatedAt()
        );
    }
}
