package com.tuxpoli.notification.domain;

import com.tuxpoli.notification.domain.model.Notification;

public interface NotificationRepository {

    Long save(Notification notification);
}
