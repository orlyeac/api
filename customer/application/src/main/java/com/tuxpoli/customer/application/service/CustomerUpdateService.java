package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.request.CustomerUpdateRequest;
import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.common.domain.exception.DuplicateException;
import com.tuxpoli.common.domain.exception.NotFoundException;

public class CustomerUpdateService {

    private final CustomerRepository customerRepository;

    public CustomerUpdateService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public IdResponse update(Long id, CustomerUpdateRequest update) {
        Customer customer = customerRepository.getCustomerById(id)
                .orElseThrow(
                        () -> new NotFoundException(
                                "the user with id [ %s ] does not exist yet".formatted(id)
                        )
                );
        if (
                update.email() != null
                && !update.email().equals(customer.getEmail())
                && customerRepository.existsCustomerByEmail(update.email())
        ) {
            throw new DuplicateException(
                    "the email [ %s ] is not available".formatted(update.email())
            );
        }
        return new IdResponse(
                customerRepository.updateCustomer(
                        customer.update(
                                update.name(),
                                update.email(),
                                update.labourLink(),
                                update.company()
                        )
                )
        );
    }
}
