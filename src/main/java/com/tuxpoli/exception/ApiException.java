package com.tuxpoli.exception;

import java.time.LocalDateTime;

public record ApiException(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
