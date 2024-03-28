package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.response.IdResponse;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.customer.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerDeleteServiceTest {

    private CustomerDeleteService underTest;

    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerDeleteService(
                customerRepository
        );
    }

    @Test
    void deleteCustomer() {
        // given
        Long id = 1L;
        when(customerRepository.existsCustomerById(id)).thenReturn(true);

        // when
        IdResponse actual = underTest.delete(id);

        // then
        verify(customerRepository).deleteCustomer(id);
        assertThat(actual).isEqualTo(new IdResponse(id));
    }

    @Test
    void deleteCustomerThrowWhenIdNotFound() {
        // given
        Long id = 1L;
        when(customerRepository.existsCustomerById(id)).thenReturn(false);

        // when / then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(NotFoundException.class);
        verify(customerRepository, never()).deleteCustomer(ArgumentMatchers.any());
    }
}