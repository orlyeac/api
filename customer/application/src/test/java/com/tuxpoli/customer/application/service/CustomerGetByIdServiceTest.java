package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.mapper.CustomerToCustomerResponseMapper;
import com.tuxpoli.customer.application.response.CustomerResponse;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.customer.domain.exception.NotFoundException;
import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.customer.domain.model.LabourLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerGetByIdServiceTest {

    private CustomerGetByIdService underTest;

    @Mock private CustomerRepository customerRepository;

    private CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    @BeforeEach
    void setUp() {
        customerToCustomerResponseMapper = new CustomerToCustomerResponseMapper();
        underTest = new CustomerGetByIdService(
                customerRepository,
                customerToCustomerResponseMapper
        );
    }

    @Test
    void getCustomer() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@mail.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));

        // when
        CustomerResponse actual = underTest.getById(id);

        // then
        verify(customerRepository).getCustomerById(id);
        assertThat(actual).isEqualTo(customerToCustomerResponseMapper.apply(customer));
    }

    @Test
    void getCustomerThrowExceptionIfNotFound() {
        // given
        Long id = 1L;
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> underTest.getById(id))
                .isInstanceOf(NotFoundException.class);
    }

}