package com.tuxpoli.customer.infrastructure.auth;

import com.tuxpoli.customer.domain.auth.AuthenticationData;
import com.tuxpoli.common.domain.LabourLink;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationUtilityAdapterTest {

    private AuthenticationUtilityAdapter underTest;

    @Mock private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationUtilityAdapter(
                authenticationManager
        );
    }

    @Test
    void authenticate() {
        // given
        String username = "johndoe@email.com";
        String password = "password";
        LocalDateTime createdAt = LocalDateTime.now();
        CustomerJPAEntity customerJPAEntity = new CustomerJPAEntity(
                1L,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        )).thenReturn(
                new UsernamePasswordAuthenticationToken(
                        customerJPAEntity,
                        password,
                        customerJPAEntity.getAuthorities()
                )
        );

        // when
        AuthenticationData authenticationData = underTest.authenticate(
                username,
                password
        );

        // then
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        assertThat(authenticationData.email()).isEqualTo(customerJPAEntity.getEmail());
        assertThat(authenticationData.authority()).isEqualTo(customerJPAEntity.getAuthority());
        assertThat(authenticationData.id()).isEqualTo(customerJPAEntity.getId());
    }
}
