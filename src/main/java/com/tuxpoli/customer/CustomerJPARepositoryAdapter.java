package com.tuxpoli.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("JPA")
public class CustomerJPARepositoryAdapter implements CustomerRepository {

    private final CustomerJPARepository customerJPARepository;

    public CustomerJPARepositoryAdapter(CustomerJPARepository customerJPARepository) {
        this.customerJPARepository = customerJPARepository;
    }


    @Override
    public boolean existsCustomerByEmail(String email) {
        return true;
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return true;
    }

    @Override
    public Long createCustomer(Customer customer) {
        return 1L;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.of(
                new Customer(
                        1L,
                        "John Doe",
                        "johndoe@email.com",
                        1994
                )
        );
    }

    @Override
    public Long updateCustomer(Customer customer) {
        return 1L;
    }

    @Override
    public Long deleteCustomer(Long id) {
        return 1L;
    }
}
