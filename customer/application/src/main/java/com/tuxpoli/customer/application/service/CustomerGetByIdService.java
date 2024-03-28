package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.mapper.CustomerToCustomerResponseMapper;
import com.tuxpoli.customer.application.response.CustomerResponse;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.customer.domain.exception.NotFoundException;

public class CustomerGetByIdService {

    private final CustomerRepository customerRepository;

    private final CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    public CustomerGetByIdService(
            CustomerRepository customerRepository,
            CustomerToCustomerResponseMapper customerToCustomerResponseMapper
    ) {
        this.customerRepository = customerRepository;
        this.customerToCustomerResponseMapper = customerToCustomerResponseMapper;
    }

    public CustomerResponse getById(Long id) {
        return customerRepository.getCustomerById(id)
                .map(customerToCustomerResponseMapper)
                .orElseThrow(
                        () -> new NotFoundException(
                                "the user with id [ %s ] does not exists yet".formatted(id)
                        )
                );
    }
}
