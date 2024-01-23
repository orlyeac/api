package com.tuxpoli.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerRepository);
    }

    @Test
    void getAllCustomers() {
        // when
        underTest.getAllCustomers();

        // then
        verify(customerRepository).getAllCustomers();
    }

    @Test
    void getCustomer() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@mail.com",
                1994
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));

        // when
        Customer actual = underTest.getCustomer(id);

        // then
        verify(customerRepository).getCustomerById(id);
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void getCustomerThrowExceptionIfNotFound() {
        // given
        Long id = 1L;
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void createCustomer() {
        // given
        String email = "johndoe@email.com";
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        Customer customer = new Customer(
                "John Doe",
                email,
                1994
        );
        Long id = 1L;
        when(customerRepository.createCustomer(customer)).thenReturn(id);

        // when
        Long actual = underTest.createCustomer(customer);

        // then
        verify(customerRepository).createCustomer(customer);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void createCustomerThrowWhenDuplicateEmail() {
        // given
        String email = "johndoe@email.com";
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(true);
        Customer customer = new Customer(
                "John Doe",
                email,
                1994
        );

        // when / then
        assertThatThrownBy(() -> underTest.createCustomer(customer))
                .isInstanceOf(CustomerDuplicateException.class);
        verify(customerRepository, never()).createCustomer(any());
    }

    @Test
    void updateCustomerAllProperties() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        String email = "johnnydoe@email.com";
        Customer update = new Customer(
                id,
                "Johnny Doe",
                email,
                1996
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        when(customerRepository.updateCustomer(update)).thenReturn(id);

        // when
        Long actual = underTest.updateCustomer(update);

        // then
        verify(customerRepository).updateCustomer(update);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void updateCustomerNameOnly() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        String email = "johndoe@email.com";
        Customer update = new Customer(
                id,
                "Johnny Doe",
                email,
                1994
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.updateCustomer(update)).thenReturn(id);

        // when
        Long actual = underTest.updateCustomer(update);

        // then
        verify(customerRepository).updateCustomer(update);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void updateCustomerEmailOnly() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        String email = "johnnydoe@email.com";
        Customer update = new Customer(
                id,
                "John Doe",
                email,
                1994
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        when(customerRepository.updateCustomer(update)).thenReturn(id);

        // when
        Long actual = underTest.updateCustomer(update);

        // then
        verify(customerRepository).updateCustomer(update);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void updateCustomerYearOfBirthOnly() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        String email = "johndoe@email.com";
        Customer update = new Customer(
                id,
                "John Doe",
                email,
                1996
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.updateCustomer(update)).thenReturn(id);

        // when
        Long actual = underTest.updateCustomer(update);

        // then
        verify(customerRepository).updateCustomer(update);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void updateCustomerThrowIfIdNotFound() {
        // given
        Long id = 1L;
        String email = "johnnydoe@email.com";
        Customer update = new Customer(
                id,
                "Johnny Doe",
                email,
                1996
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> underTest.updateCustomer(update))
                .isInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, never()).updateCustomer(any());
    }

    @Test
    void updateCustomerThrowIfDuplicateEmail() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        String email = "johnnydoe@email.com";
        Customer update = new Customer(
                id,
                "John Doe",
                email,
                1994
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(true);

        // when / then
        assertThatThrownBy(() -> underTest.updateCustomer(update))
                .isInstanceOf(CustomerDuplicateException.class);
        verify(customerRepository, never()).updateCustomer(any());
    }

    @Test
    void updateCustomerThrowIfNoChangeApplied() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        String email = "johndoe@email.com";
        Customer update = new Customer(
                id,
                "John Doe",
                email,
                1994
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));

        // when / then
        assertThatThrownBy(() -> underTest.updateCustomer(update))
                .isInstanceOf(CustomerWithoutChangeException.class);
        verify(customerRepository, never()).updateCustomer(any());
    }

    @Test
    void deleteCustomer() {
        // given
        Long id = 1L;
        when(customerRepository.existsCustomerById(id)).thenReturn(true);

        // when
        Long actual = underTest.deleteCustomer(id);

        // then
        verify(customerRepository).deleteCustomer(id);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void deleteCustomerThrowWhenIdNotFound() {
        // given
        Long id = 1L;
        when(customerRepository.existsCustomerById(id)).thenReturn(false);

        // when / then
        assertThatThrownBy(() -> underTest.deleteCustomer(id))
                .isInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, never()).deleteCustomer(any());
    }
}