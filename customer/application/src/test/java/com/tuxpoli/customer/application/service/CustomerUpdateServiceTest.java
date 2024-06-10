package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.request.CustomerUpdateRequest;
import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.common.domain.exception.DuplicateException;
import com.tuxpoli.common.domain.exception.NotFoundException;
import com.tuxpoli.common.domain.exception.WithoutChangeException;
import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.common.domain.LabourLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerUpdateServiceTest {

    private CustomerUpdateService underTest;

    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerUpdateService(
                customerRepository
        );
    }

    @Test
    void updateCustomerAllProperties() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        String email = "johnnydoe@email.com";
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                "Johnny Doe",
                email,
                LabourLink.NONE,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        when(customerRepository.updateCustomer(new Customer(
                id,
                update.name(),
                update.email(),
                customer.getPassword(),
                update.labourLink(),
                update.company(),
                true,
                "ROLE_USER",
                createdAt
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.update(id, update);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).updateCustomer(argumentCaptor.capture());
        Customer updatingItem = argumentCaptor.getValue();
        assertThat(updatingItem.getId()).isEqualTo(id);
        assertThat(updatingItem.getName()).isEqualTo(update.name());
        assertThat(updatingItem.getEmail()).isEqualTo(update.email());
        assertThat(updatingItem.getLabourLink()).isEqualTo(update.labourLink());
        assertThat(updatingItem.getCompany()).isEqualTo(update.company());
        assertThat(actual).isEqualTo(new IdResponse(id));
    }

    @Test
    void updateCustomerNameOnly() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                "Johnny Doe",
                null,
                LabourLink.NONE,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.updateCustomer(new Customer(
                id,
                update.name(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getLabourLink(),
                customer.getCompany(),
                true,
                "ROLE_USER",
                createdAt
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.update(id, update);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).updateCustomer(argumentCaptor.capture());
        Customer updatingItem = argumentCaptor.getValue();
        assertThat(updatingItem.getId()).isEqualTo(id);
        assertThat(updatingItem.getName()).isEqualTo(update.name());
        assertThat(updatingItem.getEmail()).isEqualTo(customer.getEmail());
        assertThat(updatingItem.getLabourLink()).isEqualTo(customer.getLabourLink());
        assertThat(updatingItem.getCompany()).isEqualTo(customer.getCompany());
        assertThat(actual).isEqualTo(new IdResponse(id));
    }

    @Test
    void updateCustomerEmailOnly() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        String email = "johnnydoe@email.com";
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                "John Doe",
                email,
                null,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        when(customerRepository.updateCustomer(new Customer(
                id,
                customer.getName(),
                update.email(),
                customer.getPassword(),
                customer.getLabourLink(),
                customer.getCompany(),
                true,
                "ROLE_USER",
                createdAt
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.update(id, update);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).updateCustomer(argumentCaptor.capture());
        Customer updatingItem = argumentCaptor.getValue();
        assertThat(updatingItem.getId()).isEqualTo(id);
        assertThat(updatingItem.getName()).isEqualTo(customer.getName());
        assertThat(updatingItem.getEmail()).isEqualTo(update.email());
        assertThat(updatingItem.getLabourLink()).isEqualTo(customer.getLabourLink());
        assertThat(updatingItem.getCompany()).isEqualTo(customer.getCompany());
        assertThat(actual).isEqualTo(new IdResponse(id));
    }

    @Test
    void updateCustomerLabourLinkOnly() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                null,
                "johndoe@email.com",
                LabourLink.FELLOW_STUDENT,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.updateCustomer(new Customer(
                id,
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                update.labourLink(),
                customer.getCompany(),
                true,
                "ROLE_USER",
                createdAt
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.update(id, update);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).updateCustomer(argumentCaptor.capture());
        Customer updatingItem = argumentCaptor.getValue();
        assertThat(updatingItem.getId()).isEqualTo(id);
        assertThat(updatingItem.getName()).isEqualTo(customer.getName());
        assertThat(updatingItem.getEmail()).isEqualTo(customer.getEmail());
        assertThat(updatingItem.getLabourLink()).isEqualTo(update.labourLink());
        assertThat(updatingItem.getCompany()).isEqualTo(customer.getCompany());
    assertThat(actual).isEqualTo(new IdResponse(id));
    }

    @Test
    void updateCustomerThrowIfIdNotFound() {
        // given
        Long id = 1L;
        String email = "johnnydoe@email.com";
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                "Johnny Doe",
                email,
                LabourLink.NONE,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> underTest.update(id, update))
                .isInstanceOf(NotFoundException.class);
        verify(customerRepository, never()).updateCustomer(ArgumentMatchers.any());
    }

    @Test
    void updateCustomerThrowIfDuplicateEmail() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        String email = "johnnydoe@email.com";
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                "John Doe",
                email,
                LabourLink.NONE,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(true);

        // when / then
        assertThatThrownBy(() -> underTest.update(id, update))
                .isInstanceOf(DuplicateException.class);
        verify(customerRepository, never()).updateCustomer(ArgumentMatchers.any());
    }

    @Test
    void updateCustomerThrowIfNoChangeApplied() {
        // given
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        String email = "johndoe@email.com";
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                "John Doe",
                email,
                LabourLink.NONE,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));

        // when / then
        assertThatThrownBy(() -> underTest.update(id, update))
                .isInstanceOf(WithoutChangeException.class);
        verify(customerRepository, never()).updateCustomer(ArgumentMatchers.any());
    }

}