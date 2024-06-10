package com.tuxpoli.customer.application.service;

import com.tuxpoli.common.domain.CustomerCreatedEvent;
import com.tuxpoli.common.domain.EventBus;
import com.tuxpoli.customer.application.request.CustomerCreateRequest;
import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.customer.domain.*;
import com.tuxpoli.common.domain.exception.DuplicateException;
import com.tuxpoli.customer.domain.model.Customer;

import java.time.LocalDateTime;

public class CustomerCreateService {

    private final CustomerRepository customerRepository;

    private final PasswordEncodeUtility passwordEncodeUtility;

    private final EventBus eventBus;

    public CustomerCreateService(
            CustomerRepository customerRepository,
            PasswordEncodeUtility passwordEncodeUtility,
            EventBus eventBus
    ) {
        this.customerRepository = customerRepository;
        this.passwordEncodeUtility = passwordEncodeUtility;
        this.eventBus = eventBus;
    }

    public IdResponse create(CustomerCreateRequest customerCreateRequest) {
        if (customerRepository.existsCustomerByEmail(
                customerCreateRequest.email()
        )) {
            throw new DuplicateException(
                    "the email [ %s ] is not available"
                            .formatted(customerCreateRequest.email())
            );
        }
        Customer customer = Customer.create(
                customerCreateRequest.name(),
                customerCreateRequest.email(),
                passwordEncodeUtility.encode(
                        customerCreateRequest.password()
                ),
                customerCreateRequest.labourLink(),
                customerCreateRequest.company(),
                true,
                "ROLE_USER",
                LocalDateTime.now()
        );
        Long id = customerRepository.createCustomer(customer);
        /* eventBus.publish(
                new CustomerCreatedEvent(
                        id,
                        customer.getName(),
                        customer.getEmail(),
                        customer.getLabourLink().name(),
                        customer.getCompany()
                ),
                "customer-created"
        ); */
        return new IdResponse(id);
    }
}
