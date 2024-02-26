package com.tuxpoli.customer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CustomerToCustomerResponseMapper implements Function<Customer, CustomerResponse> {
    @Override
    public CustomerResponse apply(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLabourLink(),
                customer.getCompany(),
                customer.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }
}
