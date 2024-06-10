package com.tuxpoli.customer.infrastructure.controller;

import com.tuxpoli.customer.application.service.CustomerDeleteService;
import com.tuxpoli.common.application.IdResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerDeleteController {

    private final CustomerDeleteService customerDeleteService;

    public CustomerDeleteController(CustomerDeleteService customerDeleteService) {
        this.customerDeleteService = customerDeleteService;
    }

    @DeleteMapping(path = "{id}")
    public IdResponse getCustomers(
            @PathVariable("id") Long id
    ) {
        return customerDeleteService.delete(id);
    }

}
