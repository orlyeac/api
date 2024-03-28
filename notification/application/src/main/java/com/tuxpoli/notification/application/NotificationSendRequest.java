package com.tuxpoli.notification.application;

import com.tuxpoli.notification.domain.model.NotificationKind;

public record NotificationSendRequest(
        Long toId,
        String toEmail,
        String toName,
        NotificationKind kind
) {
}
