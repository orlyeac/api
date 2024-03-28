package com.tuxpoli.customer.application.request.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
