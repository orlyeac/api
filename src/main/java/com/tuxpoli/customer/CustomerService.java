package com.tuxpoli.customer;

import com.tuxpoli.exception.DuplicateException;
import com.tuxpoli.exception.NotFoundException;
import com.tuxpoli.exception.WithoutChangeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    private final CustomerCreateRequestToCustomerMapper customerCreateRequestToCustomerMapper;

    public CustomerService(
            @Qualifier("jpa") CustomerRepository customerRepository,
            CustomerToCustomerResponseMapper customerToCustomerResponseMapper,
            CustomerCreateRequestToCustomerMapper customerCreateRequestToCustomerMapper
    ) {
        this.customerRepository = customerRepository;
        this.customerToCustomerResponseMapper = customerToCustomerResponseMapper;
        this.customerCreateRequestToCustomerMapper = customerCreateRequestToCustomerMapper;
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.getAllCustomers()
                .stream()
                .map(customerToCustomerResponseMapper)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomer(Long id) {
        return customerRepository.getCustomerById(id)
                .map(customerToCustomerResponseMapper)
                .orElseThrow(
                        () -> new NotFoundException(
                                "the user with id [ %s ] does not exists yet".formatted(id)
                        )
                );
    }

    public IdResponse createCustomer(CustomerCreateRequest customer) {
        if (customerRepository.existsCustomerByEmail(customer.email())) {
            throw new DuplicateException(
                    "the email [ %s ] is not available".formatted(customer.email())
            );
        }
        return new IdResponse(customerRepository.createCustomer(
                customerCreateRequestToCustomerMapper.apply(customer)
        ));
    }

    public IdResponse updateCustomer(Long id, CustomerUpdateRequest update) {
        Customer customer = customerRepository.getCustomerById(id)
                .orElseThrow(
                        () -> new NotFoundException(
                                "the user with id [ %s ] does not exist yet".formatted(id)
                        )
                );
        boolean modified = false;
        if (
                update.name() != null &&
                !update.name().equals(customer.getName())
        ) {
            customer.setName(update.name());
            modified = true;
        }
        if (
                update.email() != null &&
                !update.email().equals(customer.getEmail())
        ) {
            if (customerRepository.existsCustomerByEmail(update.email())) {
                throw new DuplicateException(
                        "the email [ %s ] is not available".formatted(update.email())
                );
            }
            customer.setEmail(update.email());
            modified = true;
        }
        if (
                update.labourLink() != null &&
                !update.labourLink().equals(customer.getLabourLink())
        ) {
            customer.setLabourLink(update.labourLink());
            modified = true;
        }
        if (
                update.company() != null &&
                !update.company().equals(customer.getCompany())
        ) {
            customer.setCompany(update.company());
            modified = true;
        }
        if (!modified) {
            throw new WithoutChangeException(
                    "update without change attempted"
            );
        }
        customerRepository.updateCustomer(customer);
        return new IdResponse(id);
    }

    public IdResponse deleteCustomer(Long id) {
        if (!customerRepository.existsCustomerById(id)) {
            throw new NotFoundException(
                    "the user with id [ %s ] does not exists yet".formatted(id)
            );
        }
        customerRepository.deleteCustomer(id);
        return new IdResponse(id);
    }
}
