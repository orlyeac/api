package com.tuxpoli.auth;

import com.tuxpoli.customer.IdResponse;

public record AuthenticationData(
        String token,
        IdResponse id
) {
}
