package com.tuxpoli.notification.application;

import com.tuxpoli.common.domain.CustomerCreatedEvent;
import com.tuxpoli.notification.domain.EmailSender;

import com.tuxpoli.notification.domain.NotificationRepository;
import com.tuxpoli.notification.domain.model.Notification;
import com.tuxpoli.notification.domain.model.NotificationKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationSendServiceTest {

    private NotificationSendService underTest;

    @Mock private NotificationRepository notificationRepository;

    @Mock private EmailSender emailSender;

    @BeforeEach
    void setUp() {
        underTest = new NotificationSendService(
                notificationRepository,
                emailSender
        );
    }

    @Test
    void send() {
        // given
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(
                1L,
                "John Doe",
                "johndoe@email.com",
                "NONE",
                "Tuxpoli"
        );
        LocalDateTime createdAt = LocalDateTime.now();
        Notification notification = new Notification(
                1L,
                "johndoe@email.com",
                "John Doe",
                NotificationKind.WELCOME,
                createdAt
        );
        MockedStatic<LocalDateTime> localDateTimeMockedStatic = Mockito.mockStatic(
                LocalDateTime.class,
                CALLS_REAL_METHODS
        );
        localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(createdAt);
        when(notificationRepository.save(notification)).thenReturn(1L);

        // when
        underTest.send(customerCreatedEvent);

        // then
        verify(notificationRepository).save(notification);
        verify(emailSender).sendEmail(
                notification.getToEmail(),
                notification.getKind().getSubject(),
                notification.getKind().getMessage()
                        .formatted(notification.getToName())
        );
    }
}