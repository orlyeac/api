package com.tuxpoli.customer.infrastructure.security;

import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.customer.infrastructure.mapper.CustomerToCustomerJPAEntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerUserDetailService(@Qualifier("jpa") CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.getCustomerByEmail(username).map(new CustomerToCustomerJPAEntityMapper())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "username [ %s ] is not added to database yet".formatted(username)
                ));
    }
}
