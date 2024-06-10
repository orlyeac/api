package com.tuxpoli.customer.infrastructure.security;

import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.common.domain.LabourLink;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUserDetailServiceTest {

    private CustomerUserDetailService underTest;

    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerUserDetailService(
                customerRepository
        );
    }

    @Test
    void loadUserByUsername() {
        // given
        String email = "johndoe@email.com";
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                1L,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        CustomerJPAEntity customerJPAEntity = new CustomerJPAEntity(
                1L,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        when(customerRepository.getCustomerByEmail(email)).thenReturn(
                Optional.of(customer));

        // when
        UserDetails actual = underTest.loadUserByUsername(email);

        // then
        verify(customerRepository).getCustomerByEmail(email);
        assertThat(actual).isEqualTo(customerJPAEntity);
    }

    @Test
    void loadUserByUsernameThrowIfEmailNotFound() {
        // given
        String email = "johndoe@email.com";
        when(customerRepository.getCustomerByEmail(email)).thenReturn(
                Optional.empty());

        // when / then
        assertThatThrownBy(() -> underTest.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}