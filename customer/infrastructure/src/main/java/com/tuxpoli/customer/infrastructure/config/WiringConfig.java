package com.tuxpoli.customer.infrastructure.config;

import com.tuxpoli.customer.application.mapper.CustomerToCustomerResponseMapper;
import com.tuxpoli.customer.application.service.*;
import com.tuxpoli.customer.application.service.auth.AuthenticationService;
import com.tuxpoli.customer.domain.*;
import com.tuxpoli.customer.domain.auth.AuthenticationUtility;
import com.tuxpoli.customer.infrastructure.amqp.bus.EventBusAdapter;
import com.tuxpoli.customer.infrastructure.auth.AuthenticationUtilityAdapter;
import com.tuxpoli.customer.infrastructure.security.PasswordEncodeUtilityAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WiringConfig {

    @Bean
    EventBus eventBus() {
        return new EventBusAdapter();
    }

    @Bean
    PasswordEncodeUtility passwordEncodeUtility(PasswordEncoder passwordEncoder) {
        return new PasswordEncodeUtilityAdapter(passwordEncoder);
    }

    @Bean
    CustomerCreateService customerCreateService(
            CustomerRepository customerRepository,
            PasswordEncodeUtility passwordEncodeUtility,
            EventBus eventBus
    ) {
        return new CustomerCreateService(
                customerRepository,
                passwordEncodeUtility,
                eventBus
        );
    }

    @Bean
    CustomerDeleteService customerDeleteService(CustomerRepository customerRepository) {
        return new CustomerDeleteService(customerRepository);
    }

    @Bean
    CustomerGetByIdService customerGetByIdService(
            CustomerRepository customerRepository
    ) {
        return new CustomerGetByIdService(
                customerRepository,
                new CustomerToCustomerResponseMapper()
        );
    }

    @Bean
    CustomerListService customerListService(
            CustomerRepository customerRepository
    ) {
        return new CustomerListService(
                customerRepository,
                new CustomerToCustomerResponseMapper()
        );
    }

    @Bean
    CustomerUpdateService customerUpdateService(CustomerRepository customerRepository) {
        return new CustomerUpdateService(
                customerRepository
        );
    }

    @Bean
    AuthenticationUtility authenticationUtility(AuthenticationManager authenticationManager) {
        return new AuthenticationUtilityAdapter(
                authenticationManager
        );
    }

    @Bean
    AuthenticationService authenticationService(AuthenticationUtility authenticationUtility) {
        return new AuthenticationService(
                authenticationUtility
        );
    }
}
