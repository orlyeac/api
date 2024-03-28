package com.tuxpoli.customer.infrastructure.persistence.jpa;

import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.customer.infrastructure.mapper.CustomerJPAEntityToCustomerMapper;
import com.tuxpoli.customer.infrastructure.mapper.CustomerToCustomerJPAEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("jpa")
public class CustomerJPARepositoryAdapter implements CustomerRepository {

    @Autowired
    private final CustomerJPARepository customerJPARepository;

    @Autowired
    private final CustomerToCustomerJPAEntityMapper customerToCustomerJPAEntityMapper;

    @Autowired
    private final CustomerJPAEntityToCustomerMapper customerJPAEntityToCustomerMapper;

    public CustomerJPARepositoryAdapter(
            CustomerJPARepository customerJPARepository,
            CustomerToCustomerJPAEntityMapper customerToCustomerJPAEntityMapper,
            CustomerJPAEntityToCustomerMapper customerJPAEntityToCustomerMapper
    ) {
        this.customerJPARepository = customerJPARepository;
        this.customerToCustomerJPAEntityMapper = customerToCustomerJPAEntityMapper;
        this.customerJPAEntityToCustomerMapper = customerJPAEntityToCustomerMapper;
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
        customer.setId(
                customerJPARepository.save(
                        customerToCustomerJPAEntityMapper.apply(
                                customer
                        )
                ).getId()
        );
        return customer.getId();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerJPARepository.findAll(Sort.by("id"))
                .stream()
                .map(customerJPAEntityToCustomerMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerJPARepository.findById(id)
                .map(customerJPAEntityToCustomerMapper);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerJPARepository.findCustomerByEmail(email)
                .map(customerJPAEntityToCustomerMapper);
    }

    @Override
    public Long updateCustomer(Customer customer) {
        return customerJPARepository.save(
                customerToCustomerJPAEntityMapper.apply(customer)
        ).getId();
    }

    @Override
    public Long deleteCustomer(Long id) {
        customerJPARepository.deleteById(id);
        return id;
    }
}
