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
        return new ArrayList<>();
    }

    public Customer getCustomer(Long id) {
        return new Customer();
    }

    public Long createCustomer(Customer customer) {
        return 1L;
    }

    public Long updateCustomer(Customer customer) {
        return 1L;
    }

    public Long deleteCustomer(Long id) {
        return id;
    }
}
