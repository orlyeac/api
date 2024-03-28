package com.tuxpoli.customer.infrastructure.security;

import com.tuxpoli.customer.infrastructure.mapper.CustomerToCustomerJPAEntityMapper;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import com.tuxpoli.customer.domain.CustomerRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
        CustomerJPAEntity customerJPAEntity = customerRepository.getCustomerByEmail(
                authentication.getName()
        )
                .map(new CustomerToCustomerJPAEntityMapper())
                .orElseThrow(
                () -> new BadCredentialsException(
                        "username used for authentication is not available"
                ));
        return id.equals(customerJPAEntity.getId());
    }
}
