package com.tuxpoli.customer.microserviceEntry.customer;

import com.tuxpoli.customer.domain.model.LabourLink;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPARepository;
import com.tuxpoli.customer.microserviceEntry.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerJPARepositoryTest extends TestcontainersConfig {

    @Autowired
    private CustomerJPARepository underTest;

    @Test
    void existsCustomerByEmail() {
        // given
        String email = "johndoe@email.com";
        CustomerJPAEntity customerJPAEntity = new CustomerJPAEntity(
                "John Doe",
                email,
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                LocalDateTime.now()
        );
        underTest.save(customerJPAEntity);

        // when
        boolean exists = underTest.existsCustomerByEmail(email);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailNotPresent() {
        // given
        String email = "johndoe@email.com";

        // when
        boolean exists = underTest.existsCustomerByEmail(email);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void existsCustomerById() {
        // given
        CustomerJPAEntity customerJPAEntity = new CustomerJPAEntity(
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                LocalDateTime.now()

        );
        Long id = underTest.save(customerJPAEntity).getId();

        // when
        boolean exists = underTest.existsCustomerById(id);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void existsCustomerByIdFailsWhenIdNotPresent() {
        // given
        Long id = 1L;

        // when
        boolean exists = underTest.existsCustomerById(id);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void findCustomerByEmail() {
        // given
        String email = "johndoe@email.com";
        CustomerJPAEntity customerJPAEntity = underTest.save(new CustomerJPAEntity(
                "John Doe",
                email,
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne",
                true,
                "ROLE_USER",
                LocalDateTime.now()

        ));

        // when
        Optional<CustomerJPAEntity> actual = underTest.findCustomerByEmail(email);

        // then
        assertThat(actual).isEqualTo(Optional.of(customerJPAEntity));
    }

    @Test
    void findCustomerByEmailEmptyWhenEmailNotPresent() {
        // given
        String email = "johndoe@email.com";

        // when
        Optional<CustomerJPAEntity> actual = underTest.findCustomerByEmail(email);

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }
}