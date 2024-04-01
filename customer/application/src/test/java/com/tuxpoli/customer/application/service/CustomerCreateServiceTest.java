package com.tuxpoli.customer.application.service;

import com.tuxpoli.customer.application.request.CustomerCreateRequest;
import com.tuxpoli.customer.application.response.IdResponse;
import com.tuxpoli.customer.domain.CustomerRepository;
import com.tuxpoli.common.domain.EventBus;
import com.tuxpoli.customer.domain.PasswordEncodeUtility;
import com.tuxpoli.customer.domain.exception.DuplicateException;
import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.customer.domain.model.LabourLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerCreateServiceTest {

    private CustomerCreateService underTest;

    @Mock private CustomerRepository customerRepository;

    @Mock private PasswordEncodeUtility passwordEncodeUtility;

    @Mock private EventBus eventBus;

    @BeforeEach
    void setUp() {
        underTest = new CustomerCreateService(
                customerRepository,
                passwordEncodeUtility,
                eventBus
        );
    }

    @Test
    void createCustomer() {
        // given
        String name = "John Doe";
        String email = "johndoe@email.com";
        LabourLink labourLink = LabourLink.NONE;
        String company = "CompanyOne";
        CustomerCreateRequest
                customerCreateRequest = new CustomerCreateRequest(
                name,
                email,
                "password",
                labourLink,
                company
        );
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(
                name,
                email,
                "hashedpassword",
                labourLink,
                company,
                true,
                "ROLE_USER",
                createdAt
        );
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        when(passwordEncodeUtility.encode(customerCreateRequest.password())).thenReturn(
                "hashed%s".formatted(customerCreateRequest.password())
        );
        MockedStatic<LocalDateTime> localDateTimeMockedStatic = Mockito.mockStatic(
                LocalDateTime.class,
                CALLS_REAL_METHODS
        );
        localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(createdAt);
        when(
                customerRepository.createCustomer(
                        customer
                )
        ).thenReturn(id);

        // when
        IdResponse actual = underTest.create(customerCreateRequest);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).createCustomer(argumentCaptor.capture());
        Customer insertNew = argumentCaptor.getValue();
        assertThat(insertNew.getName()).isEqualTo(customerCreateRequest.name());
        assertThat(insertNew.getEmail()).isEqualTo(customerCreateRequest.email());
        assertThat(insertNew.getPassword()).isEqualTo(passwordEncodeUtility.encode(customerCreateRequest.password()));
        assertThat(insertNew.getLabourLink()).isEqualTo(customerCreateRequest.labourLink());
        assertThat(insertNew.getCompany()).isEqualTo(customerCreateRequest.company());
        assertThat(actual).isEqualTo(new IdResponse(id));
    }

    @Test
    void createCustomerThrowWhenDuplicateEmail() {
        // given
        String email = "johndoe@email.com";
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(true);
        CustomerCreateRequest customer = new CustomerCreateRequest(
                "John Doe",
                email,
                "password",
                LabourLink.NONE,
                "CompanyOne"
        );

        // when / then
        assertThatThrownBy(() -> underTest.create(customer))
                .isInstanceOf(DuplicateException.class);
        verify(customerRepository, never()).createCustomer(ArgumentMatchers.any());
    }

}
