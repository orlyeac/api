package com.tuxpoli.notification.domain.model;

public enum NotificationKind {
    WELCOME(
            "WELCOME",
            "Welcome to Tuxpoli",
            "Hello %s.\n\n" +
                    "We are thrilled to give you our welcome to Tuxpoli: the Orlando Abreu portfolio.\n\n" +
                    "Best regards.\n" +
                    "Tuxpoli team"
    );

    private final String value;
    private final String subject;
    private final String message;

    private NotificationKind(String value, String subject, String message) {
        this.value = value;
        this.subject = subject;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return value;
    }
}
