package com.tuxpoli.auth;

import com.tuxpoli.customer.Customer;
import com.tuxpoli.customer.IdResponse;
import com.tuxpoli.jwt.JWTUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock private AuthenticationManager authenticationManager;

    @Mock private JWTUtility jwtUtility;

    private AuthenticationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(authenticationManager, jwtUtility);
    }

    @Test
    void login() {
        // given
        Customer customer = new Customer(
                1L,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                1994
        );
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "johndoe@email.com",
                "password"
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customer,
                authenticationRequest.password(),
                customer.getAuthorities());
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        )).thenReturn(authentication);
        when(jwtUtility.issueToken(
                customer.getEmail(),
                customer.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        )).thenReturn("thetokenissued");

        // when
        AuthenticationData authenticationData = underTest.login(authenticationRequest);

        // then
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );
        assertThat(authenticationData.token()).isEqualTo("thetokenissued");
        assertThat(authenticationData.id()).isEqualTo(new IdResponse(customer.getId()));
    }

    @Test
    void loginThrowWhenWrongPasswordUsername() {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "johndoe@email.com",
                "password"
        );
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        )).thenThrow(new BadCredentialsException("text"));

        // when / then
        assertThatThrownBy(() -> underTest.login(authenticationRequest))
                .isInstanceOf(BadCredentialsException.class);
    }
}
