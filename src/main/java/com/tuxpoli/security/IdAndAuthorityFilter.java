package com.tuxpoli.security;

import com.tuxpoli.customer.Customer;
import com.tuxpoli.customer.CustomerRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class IdAndAuthorityFilter {

    private final CustomerRepository customerRepository;

    public IdAndAuthorityFilter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean apply(Authentication authentication, Long id) {
        if (authentication.getAuthorities()
                .stream()
                .anyMatch(
                        (i) -> i.getAuthority().equals("ROLE_ADMIN")
                )
        ) {
            return true;
        }
        Customer customer = customerRepository.getCustomerByEmail(
                authentication.getName()
        ).orElseThrow(
                () -> new BadCredentialsException(
                        "username used for authentication is not available"
                ));
        return id.equals(customer.getId());
    }
}
