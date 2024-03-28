package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.mapper.CustomerToCustomerResponseMapper;
import com.tuxpoli.customer.domain.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerListServiceTest {

    private CustomerListService underTest;

    @Mock private CustomerRepository customerRepository;

    private CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    @BeforeEach
    void setUp() {
        customerToCustomerResponseMapper = new CustomerToCustomerResponseMapper();
        underTest = new CustomerListService(
                customerRepository,
                customerToCustomerResponseMapper
        );
    }

    @Test
    void getAllCustomers() {
        // when
        underTest.getAll();

        // then
        verify(customerRepository).getAllCustomers();
    }

}