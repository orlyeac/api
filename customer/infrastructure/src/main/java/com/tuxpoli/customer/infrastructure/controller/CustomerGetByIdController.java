package com.tuxpoli.customer.infrastructure.controller;

import com.tuxpoli.customer.application.service.CustomerGetByIdService;
import com.tuxpoli.common.application.CustomerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerGetByIdController {

    private final CustomerGetByIdService customerGetByIdService;

    public CustomerGetByIdController(CustomerGetByIdService customerGetByIdService) {
        this.customerGetByIdService = customerGetByIdService;
    }

    @GetMapping(path = "{id}")
    public CustomerResponse getCustomers(@PathVariable("id") Long id) {
        return customerGetByIdService.getById(id);
    }

}
