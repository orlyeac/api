package com.tuxpoli.customer;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerToCustomerResponseMapper implements Function<Customer, CustomerResponse> {
    @Override
    public CustomerResponse apply(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getYearOfBirth()
        );
    }
}
