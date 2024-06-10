package com.tuxpoli.customer.infrastructure.persistence.jpa;

import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.common.domain.LabourLink;
import com.tuxpoli.customer.infrastructure.mapper.CustomerJPAEntityToCustomerMapper;
import com.tuxpoli.customer.infrastructure.mapper.CustomerToCustomerJPAEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

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
        underTest = new CustomerJPARepositoryAdapter(
                customerJPARepository,
                new CustomerToCustomerJPAEntityMapper(),
                new CustomerJPAEntityToCustomerMapper()
        );
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
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
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
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                createdAt
        );
        when(customerJPARepository.save(customerJPAEntity)).thenReturn(
                new CustomerJPAEntity(
                        id,
                        customerJPAEntity.getName(),
                        customerJPAEntity.getEmail(),
                        customerJPAEntity.getPassword(),
                        customerJPAEntity.getLabourLink(),
                        customerJPAEntity.getCompany(),
                        customerJPAEntity.getActive(),
                        customerJPAEntity.getAuthority(),
                        customerJPAEntity.getCreatedAt()
                )
        );

        // when
        Long actual = underTest.createCustomer(customer);

        // then
        verify(customerJPARepository).save(customerJPAEntity);
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void getAllCustomers() {
        // when
        underTest.getAllCustomers();

        // then
        verify(customerJPARepository).findAll(Sort.by("id"));
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
    void getCustomerByEmail() {
        // given
        String email = "johndoe@email.com";

        // when
        underTest.getCustomerByEmail(email);

        // then
        verify(customerJPARepository).findCustomerByEmail(email);
    }

    @Test
    void updateCustomer() {
        // given
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
        when(customerJPARepository.save(customerJPAEntity)).thenReturn(customerJPAEntity);

        // when
        underTest.updateCustomer(customer);

        // then
        verify(customerJPARepository).save(customerJPAEntity);
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