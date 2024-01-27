package com.tuxpoli.customer;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerCreateRequestToCustomerMapper implements Function<CustomerCreateRequest, Customer> {
    @Override
    public Customer apply(CustomerCreateRequest customerCreateRequest) {
        return new Customer(
                customerCreateRequest.name(),
                customerCreateRequest.email(),
                customerCreateRequest.yearOfBirth()
        );
    }
}
