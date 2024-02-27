package com.tuxpoli.customer;

import com.tuxpoli.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        Customer customer = new Customer(
                "John Doe",
                email,
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
        );
        underTest.save(customer);

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
        Customer customer = new Customer(
                "John Doe",
                "johndoe@email.com",
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
        );
        Long id = underTest.save(customer).getId();

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
        Customer customer = underTest.save(new Customer(
                "John Doe",
                email,
                "hashedpassword",
                LabourLink.NONE,
                "CompanyOne"
        ));

        // when
        Optional<Customer> actual = underTest.findCustomerByEmail(email);

        // then
        assertThat(actual).isEqualTo(Optional.of(customer));
    }

    @Test
    void findCustomerByEmailEmptyWhenEmailNotPresent() {
        // given
        String email = "johndoe@email.com";

        // when
        Optional<Customer> actual = underTest.findCustomerByEmail(email);

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }
}
