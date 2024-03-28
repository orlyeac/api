package com.tuxpoli.notification.infrastructure.persistence.jpa;

import com.tuxpoli.notification.domain.model.Notification;
import com.tuxpoli.notification.domain.NotificationRepository;
import com.tuxpoli.notification.infrastructure.mapper.NotificationToNotificationJPAEntityMapper;
import org.springframework.stereotype.Repository;

@Repository("notification-jpa")
public class NotificationJPARepositoryAdapter implements NotificationRepository {

    private final NotificationJPARepository notificationJPARepository;
    private final NotificationToNotificationJPAEntityMapper notificationToNotificationJPAEntityMapper;

    public NotificationJPARepositoryAdapter(NotificationJPARepository notificationJPARepository, NotificationToNotificationJPAEntityMapper notificationToNotificationJPAEntityMapper) {
        this.notificationJPARepository = notificationJPARepository;
        this.notificationToNotificationJPAEntityMapper = notificationToNotificationJPAEntityMapper;
    }

    public Long save(Notification notification) {
        notification.setId(
                notificationJPARepository.save(
                        notificationToNotificationJPAEntityMapper.apply(
                                notification
                        )
                ).getId()
        );
        return notification.getId();
    }
}
