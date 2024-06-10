package com.tuxpoli.customer.infrastructure.controller;

import com.tuxpoli.customer.application.service.CustomerListService;
import com.tuxpoli.common.application.CustomerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerListController {

    private final CustomerListService customerListService;

    public CustomerListController(CustomerListService customerListService) {
        this.customerListService = customerListService;
    }

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerListService.getAll();
    }

}
