package com.tuxpoli.notification.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJPARepository extends JpaRepository<NotificationJPAEntity, Long> {
}
