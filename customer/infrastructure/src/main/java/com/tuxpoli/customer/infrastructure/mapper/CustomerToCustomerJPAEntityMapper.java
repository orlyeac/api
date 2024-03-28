package com.tuxpoli.customer.infrastructure.mapper;

import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerToCustomerJPAEntityMapper implements Function<Customer, CustomerJPAEntity> {

    public CustomerJPAEntity apply(Customer customer) {
        return new CustomerJPAEntity(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getLabourLink(),
                customer.getCompany(),
                customer.getActive(),
                customer.getAuthority(),
                customer.getCreatedAt()
        );
    }
}
