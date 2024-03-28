package com.tuxpoli.customer.infrastructure.mapper;

import com.tuxpoli.customer.domain.model.Customer;
import com.tuxpoli.customer.infrastructure.persistence.jpa.CustomerJPAEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerJPAEntityToCustomerMapper implements Function<CustomerJPAEntity, Customer> {

    public Customer apply(CustomerJPAEntity customerJPAEntity) {
        return new Customer(
                customerJPAEntity.getId(),
                customerJPAEntity.getName(),
                customerJPAEntity.getEmail(),
                customerJPAEntity.getPassword(),
                customerJPAEntity.getLabourLink(),
                customerJPAEntity.getCompany(),
                customerJPAEntity.getActive(),
                customerJPAEntity.getAuthority(),
                customerJPAEntity.getCreatedAt()
        );
    }
}
