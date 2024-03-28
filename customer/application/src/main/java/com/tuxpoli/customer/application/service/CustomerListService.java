package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.mapper.CustomerToCustomerResponseMapper;
import com.tuxpoli.customer.application.response.CustomerResponse;
import com.tuxpoli.customer.domain.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerListService {

    private final CustomerRepository customerRepository;

    private final CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    public CustomerListService(
            CustomerRepository customerRepository,
            CustomerToCustomerResponseMapper customerToCustomerResponseMapper
    ) {
        this.customerRepository = customerRepository;
        this.customerToCustomerResponseMapper = customerToCustomerResponseMapper;
    }

    public List<CustomerResponse> getAll() {
        return customerRepository.getAllCustomers()
                .stream()
                .map(customerToCustomerResponseMapper)
                .collect(Collectors.toList());
    }
}
