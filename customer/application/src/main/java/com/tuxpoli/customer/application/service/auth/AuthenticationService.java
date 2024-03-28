package com.tuxpoli.customer.application.service.auth;

import com.tuxpoli.customer.domain.auth.AuthenticationData;
import com.tuxpoli.customer.application.request.auth.AuthenticationRequest;
import com.tuxpoli.customer.domain.auth.AuthenticationUtility;

public class AuthenticationService {

    private final AuthenticationUtility authenticationUtility;

    public AuthenticationService(AuthenticationUtility authenticationUtility) {
        this.authenticationUtility = authenticationUtility;
    }

    public AuthenticationData login(AuthenticationRequest authenticationRequest) {
        return authenticationUtility.authenticate(
                authenticationRequest.username(),
                authenticationRequest.password()
        );
    }
}
