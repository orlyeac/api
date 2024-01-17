package com.tuxpoli.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJPARepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);
}
