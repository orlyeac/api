package com.tuxpoli.customer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerCreateRequestToCustomerMapper implements Function<CustomerCreateRequest, Customer> {

    private final PasswordEncoder passwordEncoder;

    public CustomerCreateRequestToCustomerMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer apply(CustomerCreateRequest customerCreateRequest) {
        return new Customer(
                customerCreateRequest.name(),
                customerCreateRequest.email(),
                passwordEncoder.encode(customerCreateRequest.password()),
                customerCreateRequest.labourLink(),
                customerCreateRequest.company()
        );
    }
}
