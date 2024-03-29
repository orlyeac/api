package com.tuxpoli.customer.application.mapper;

import com.tuxpoli.customer.application.response.CustomerResponse;
import com.tuxpoli.customer.domain.model.Customer;

import java.util.function.Function;

public class CustomerToCustomerResponseMapper implements Function<Customer, CustomerResponse> {

    public CustomerResponse apply(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLabourLink(),
                customer.getCompany(),
                customer.getActive(),
                customer.getAuthority(),
                customer.getCreatedAt()
        );
    }
}
