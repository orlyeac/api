package com.tuxpoli.customer.infrastructure.auth;

import com.tuxpoli.customer.domain.auth.AuthenticationData;
import com.tuxpoli.customer.domain.auth.AuthenticationUtility;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationUtilityAdapter implements AuthenticationUtility {

    private final AuthenticationManager authenticationManager;

    public AuthenticationUtilityAdapter(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationData authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        CustomerJPAEntity customerJPAEntity = (CustomerJPAEntity) authentication.getPrincipal();
        return new AuthenticationData(
                customerJPAEntity.getEmail(),
                customerJPAEntity.getAuthority(),
                customerJPAEntity.getId());
    }
}
