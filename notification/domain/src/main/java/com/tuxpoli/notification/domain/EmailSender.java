package com.tuxpoli.notification.domain;

public interface EmailSender {

    void sendEmail(
            String to,
            String subject,
            String text
    );
}
