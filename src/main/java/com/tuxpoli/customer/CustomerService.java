package com.tuxpoli.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(@Qualifier("jpa") CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.getAllCustomers();
    }

    public Customer getCustomer(Long id) {
        return this.customerRepository.getCustomerById(id).orElseThrow(
                () -> new CustomerNotFoundException(
                        "the user with id [ %s ] does not exists yet".formatted(id)
                )
        );
    }

    public Long createCustomer(Customer customer) {
        if (this.customerRepository.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerDuplicateException(
                    "the email [ %s ] is not available".formatted(customer.getEmail())
            );
        }
        return this.customerRepository.createCustomer(customer);
    }

    public Long updateCustomer(Customer update) {
        Customer customer = getCustomer(update.getId());
        boolean modified = false;
        if (
                update.getName() != null &&
                !update.getName().equals(customer.getName())
        ) {
            customer.setName(update.getName());
            modified = true;
        }
        if (
                update.getEmail() != null &&
                !update.getEmail().equals(customer.getEmail())
        ) {
            if (this.customerRepository.existsCustomerByEmail(update.getEmail())) {
                throw new CustomerDuplicateException(
                        "the email [ %s ] is not available".formatted(update.getEmail())
                );
            }
            customer.setEmail(update.getEmail());
            modified = true;
        }
        if (
                update.getYearOfBirth() != null &&
                !update.getYearOfBirth().equals(customer.getYearOfBirth())
        ) {
            customer.setYearOfBirth(update.getYearOfBirth());
            modified = true;
        }
        if (!modified) {
            throw new CustomerWithoutChangeException(
                    "update without change attempted"
            );
        }
        this.customerRepository.updateCustomer(customer);
        return update.getId();
    }

    public Long deleteCustomer(Long id) {
        if (!this.customerRepository.existsCustomerById(id)) {
            throw new CustomerNotFoundException(
                    "the user with id [ %s ] does not exists yet".formatted(id)
            );
        }
        this.customerRepository.deleteCustomer(id);
        return id;
    }
}
