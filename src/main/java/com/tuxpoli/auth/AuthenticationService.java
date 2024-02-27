package com.tuxpoli.auth;

import com.tuxpoli.customer.Customer;
import com.tuxpoli.customer.IdResponse;
import com.tuxpoli.jwt.JWTUtility;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JWTUtility jwtUtility;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JWTUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    public AuthenticationData login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );
        Customer customer = (Customer) authentication.getPrincipal();
        return new AuthenticationData(
                jwtUtility.issueToken(
                        customer.getEmail(),
                        customer.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                ),
                new IdResponse(customer.getId()));
    }
}
