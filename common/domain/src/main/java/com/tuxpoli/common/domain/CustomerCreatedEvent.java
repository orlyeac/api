package com.tuxpoli.common.domain;

public record CustomerCreatedEvent(
        Long id,
        String name,
        String email,
        String labourLink,
        String company
) {
}
