package com.tuxpoli.customer.domain;

import com.tuxpoli.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);

    Long createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> getCustomerByEmail(String email);

    Long updateCustomer(Customer customer);

    Long deleteCustomer(Long id);
}
