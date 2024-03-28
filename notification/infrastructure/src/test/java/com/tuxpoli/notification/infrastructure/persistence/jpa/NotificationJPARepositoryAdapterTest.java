package com.tuxpoli.notification.infrastructure.persistence.jpa;

import com.tuxpoli.notification.domain.model.Notification;
import com.tuxpoli.notification.domain.model.NotificationKind;
import com.tuxpoli.notification.infrastructure.mapper.NotificationToNotificationJPAEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationJPARepositoryAdapterTest {

    private NotificationJPARepositoryAdapter underTest;

    @Mock private NotificationJPARepository notificationJPARepository;

    private NotificationToNotificationJPAEntityMapper notificationToNotificationJPAEntityMapper;

    @BeforeEach
    void setUp() {
        notificationToNotificationJPAEntityMapper = new NotificationToNotificationJPAEntityMapper();
        underTest = new NotificationJPARepositoryAdapter(
                notificationJPARepository,
                notificationToNotificationJPAEntityMapper
        );
    }

    @Test
    void save() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();
        Notification notification = new Notification(
                1L,
                "johndoe@email.com",
                "John Doe",
                NotificationKind.WELCOME,
                createdAt
        );
        NotificationJPAEntity notificationJPAEntity = new NotificationJPAEntity(
                1L,
                "johndoe@email.com",
                "John Doe",
                NotificationKind.WELCOME,
                createdAt
        );
        when(notificationJPARepository.save(
                notificationToNotificationJPAEntityMapper.apply(notification)
        )).thenReturn(new NotificationJPAEntity(
                1L,
                notificationJPAEntity.getToId(),
                notificationJPAEntity.getToEmail(),
                notificationJPAEntity.getToName(),
                notificationJPAEntity.getKind(),
                notificationJPAEntity.getCreatedAt()
        ));

        // when
        Long actual = underTest.save(notification);

        // then
        verify(notificationJPARepository).save(
                notificationJPAEntity
        );
        assertThat(actual).isEqualTo(1L);
    }
}
