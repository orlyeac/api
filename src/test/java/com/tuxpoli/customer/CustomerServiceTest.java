package com.tuxpoli.customer;

import com.tuxpoli.exception.DuplicateException;
import com.tuxpoli.exception.NotFoundException;
import com.tuxpoli.exception.WithoutChangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock private CustomerRepository customerRepository;

    @Mock private PasswordEncoder passwordEncoder;

    private CustomerCreateRequestToCustomerMapper customerCreateRequestToCustomerMapper;

    private CustomerToCustomerResponseMapper customerToCustomerResponseMapper;

    @BeforeEach
    void setUp() {
        customerCreateRequestToCustomerMapper = new CustomerCreateRequestToCustomerMapper(
                passwordEncoder
        );
        customerToCustomerResponseMapper = new CustomerToCustomerResponseMapper();
        underTest = new CustomerService(
                customerRepository,
                customerToCustomerResponseMapper,
                customerCreateRequestToCustomerMapper
        );
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
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
        );
        when(customerRepository.getCustomerById(id)).thenReturn(Optional.of(customer));

        // when
        CustomerResponse actual = underTest.getCustomer(id);

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
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createCustomer() {
        // given
        String email = "johndoe@email.com";
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);
        CustomerCreateRequest customer = new CustomerCreateRequest(
                "John Doe",
                email,
                "password",
                LabourLink.NONE,
                "CompanyOne"
        );
        Long id = 1L;
        when(passwordEncoder.encode(customer.password())).thenReturn(
                "hashed%s".formatted(customer.password())
        );
        when(
                customerRepository.createCustomer(
                        customerCreateRequestToCustomerMapper.apply(customer)
                )
        ).thenReturn(id);

        // when
        IdResponse actual = underTest.createCustomer(customer);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).createCustomer(argumentCaptor.capture());
        Customer insertNew = argumentCaptor.getValue();
        assertThat(insertNew.getName()).isEqualTo(customer.name());
        assertThat(insertNew.getEmail()).isEqualTo(customer.email());
        assertThat(insertNew.getPassword()).isEqualTo(passwordEncoder.encode(customer.password()));
        assertThat(insertNew.getLabourLink()).isEqualTo(customer.labourLink());
        assertThat(insertNew.getCompany()).isEqualTo(customer.company());
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
        assertThatThrownBy(() -> underTest.createCustomer(customer))
                .isInstanceOf(DuplicateException.class);
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
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
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
                update.company()
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.updateCustomer(id, update);

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
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
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
                customer.getCompany()
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.updateCustomer(id, update);

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
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
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
                customer.getCompany()
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.updateCustomer(id, update);

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
        Customer customer = new Customer(
                id,
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
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
                customer.getCompany()
        ))).thenReturn(id);

        // when
        IdResponse actual = underTest.updateCustomer(id, update);

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
        assertThatThrownBy(() -> underTest.updateCustomer(id, update))
                .isInstanceOf(NotFoundException.class);
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
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
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
        assertThatThrownBy(() -> underTest.updateCustomer(id, update))
                .isInstanceOf(DuplicateException.class);
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
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
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
        assertThatThrownBy(() -> underTest.updateCustomer(id, update))
                .isInstanceOf(WithoutChangeException.class);
        verify(customerRepository, never()).updateCustomer(any());
    }

    @Test
    void deleteCustomer() {
        // given
        Long id = 1L;
        when(customerRepository.existsCustomerById(id)).thenReturn(true);

        // when
        IdResponse actual = underTest.deleteCustomer(id);

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
        assertThatThrownBy(() -> underTest.deleteCustomer(id))
                .isInstanceOf(NotFoundException.class);
        verify(customerRepository, never()).deleteCustomer(any());
    }
}
