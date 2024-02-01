package com.tuxpoli.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
