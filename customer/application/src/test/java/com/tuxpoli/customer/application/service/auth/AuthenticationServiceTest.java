package com.tuxpoli.customer.application.service.auth;

import com.tuxpoli.customer.application.request.auth.AuthenticationRequest;
import com.tuxpoli.customer.domain.auth.AuthenticationData;
import com.tuxpoli.customer.domain.auth.AuthenticationUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService underTest;

    @Mock private AuthenticationUtility authenticationUtility;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(
                authenticationUtility
        );
    }

    @Test
    void login() {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "johndoe@email.com",
                "password"
        );
        when(authenticationUtility.authenticate(
                authenticationRequest.username(),
                authenticationRequest.password()
        )).thenReturn(new AuthenticationData(
                "johndoe@email.com",
                "ROLE_USER",
                1L
        ));

        // when
        underTest.login(authenticationRequest);

        // then
        verify(authenticationUtility).authenticate(
                authenticationRequest.username(),
                authenticationRequest.password()
        );
    }

    @Test
    void loginThrowWhenWrongPasswordUsername() {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "johndoe@email.com",
                "password"
        );
        when(authenticationUtility.authenticate(
                authenticationRequest.username(),
                authenticationRequest.password()
        )).thenThrow();

        // when / then
        assertThatThrownBy(() -> underTest.login(authenticationRequest));
    }
}