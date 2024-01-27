package com.tuxpoli.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPARepositoryAdapter implements CustomerRepository {

    private final CustomerJPARepository customerJPARepository;

    public CustomerJPARepositoryAdapter(CustomerJPARepository customerJPARepository) {
        this.customerJPARepository = customerJPARepository;
    }


    @Override
    public boolean existsCustomerByEmail(String email) {
        return customerJPARepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customerJPARepository.existsCustomerById(id);
    }

    @Override
    public Long createCustomer(Customer customer) {
        return customerJPARepository.save(customer).getId();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJPARepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerJPARepository.findById(id);
    }

    @Override
    public Long updateCustomer(Customer customer) {
        return customerJPARepository.save(customer).getId();
    }

    @Override
    public Long deleteCustomer(Long id) {
        customerJPARepository.deleteById(id);
        return id;
    }
}
