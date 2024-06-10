package com.tuxpoli.customer.application.service;

import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.common.domain.exception.NotFoundException;

public class CustomerDeleteService {

    private final CustomerRepository customerRepository;

    public CustomerDeleteService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public IdResponse delete(Long id) {
        if (!customerRepository.existsCustomerById(id)) {
            throw new NotFoundException(
                    "the user with id [ %s ] does not exists yet".formatted(id)
            );
        }
        customerRepository.deleteCustomer(id);
        return new IdResponse(id);
    }
}
