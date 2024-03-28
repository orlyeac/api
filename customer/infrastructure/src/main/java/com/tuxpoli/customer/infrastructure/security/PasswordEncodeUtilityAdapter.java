package com.tuxpoli.customer.infrastructure.security;

import com.tuxpoli.customer.domain.PasswordEncodeUtility;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncodeUtilityAdapter implements PasswordEncodeUtility {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncodeUtilityAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(CharSequence password) {
        return passwordEncoder.encode(password);
    }
}
