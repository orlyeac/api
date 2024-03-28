package com.tuxpoli.customer.domain.auth;

public record AuthenticationData(
        String email,
        String authority,
        Long id
) {
}
