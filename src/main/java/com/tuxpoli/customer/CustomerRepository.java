package com.tuxpoli.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);

    Long createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Long updateCustomer(Customer customer);

    Long deleteCustomer(Long id);
}
