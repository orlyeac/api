package com.tuxpoli.customer.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJPARepository extends JpaRepository<CustomerJPAEntity, Long> {

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Long id);

    Optional<CustomerJPAEntity> findCustomerByEmail(String email);
}
