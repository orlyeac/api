package com.tuxpoli.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerJPARepositoryAdapterTest {

    private AutoCloseable autoCloseable;

    private CustomerJPARepositoryAdapter underTest;

    @Mock private CustomerJPARepository customerJPARepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPARepositoryAdapter(customerJPARepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void existsCustomerByEmail() {
        // given
        String email = "johndoe@email.com";

        // when
        underTest.existsCustomerByEmail(email);

        // then
        verify(customerJPARepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerById() {
        // given
        Long id = 1L;

        // when
        underTest.existsCustomerById(id);

        // then
        verify(customerJPARepository).existsCustomerById(id);
    }

    @Test
    void createCustomer() {
        // given
        Long id = 1L;
        Customer customer = new Customer(
                "John Doe",
                "johndoe@email.com",
                1994
        );
        when(customerJPARepository.save(customer)).thenReturn(
                new Customer(
                        id,
                        customer.getName(),
                        customer.getEmail(),
                        customer.getYearOfBirth()
                )
        );

        // when
        Long actual = underTest.createCustomer(customer);

        // then
        verify(customerJPARepository).save(customer);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void getAllCustomers() {
        // when
        underTest.getAllCustomers();

        // then
        verify(customerJPARepository).findAll();
    }

    @Test
    void getCustomerById() {
        // given
        Long id = 1L;

        // when
        underTest.getCustomerById(id);

        // then
        verify(customerJPARepository).findById(id);
    }

    @Test
    void updateCustomer() {
        // given
        Customer customer = new Customer(
                1L,
                "John Doe",
                "johndoe@email.com",
                1994
        );
        when(customerJPARepository.save(customer)).thenReturn(customer);

        // when
        underTest.updateCustomer(customer);

        // then
        verify(customerJPARepository).save(customer);
    }

    @Test
    void deleteCustomer() {
        // given
        Long id = 1L;

        // when
        underTest.deleteCustomer(id);

        // then
        verify(customerJPARepository).deleteById(id);
    }
}
