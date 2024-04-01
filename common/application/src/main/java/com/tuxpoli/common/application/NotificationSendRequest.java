package com.tuxpoli.common.application;

import com.tuxpoli.common.domain.NotificationKind;

public record NotificationSendRequest(
        Long toId,
        String toEmail,
        String toName,
        NotificationKind kind
) {
}
