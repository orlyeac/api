package com.tuxpoli.customer.infrastructure.controller.auth;

import com.tuxpoli.customer.application.request.auth.AuthenticationRequest;
import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.customer.domain.auth.AuthenticationData;
import com.tuxpoli.customer.application.service.auth.AuthenticationService;
import com.tuxpoli.axiom.infrastructure.jwt.JWTUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final JWTUtility jwtUtility;

    public AuthenticationController(
            AuthenticationService authenticationService,
            JWTUtility jwtUtility
    ) {
        this.authenticationService = authenticationService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationData response = authenticationService.login(request);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtUtility.issueToken(
                                response.id().toString(),
                                response.email(),
                                response.authority()
                        )
                )
                .body(new IdResponse(response.id()));
    }
}
