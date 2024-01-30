package com.tuxpoli.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerJPARepository extends JpaRepository<Customer, Long> {

    // List<Customer> findAllCustomerOrderById();

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);

    Optional<Customer> findCustomerByEmail(String email);
}
