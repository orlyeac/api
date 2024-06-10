package com.tuxpoli.customer.infrastructure.controller;

import com.tuxpoli.customer.application.service.CustomerUpdateService;
import com.tuxpoli.customer.application.request.CustomerUpdateRequest;
import com.tuxpoli.common.application.IdResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerUpdateController {

    private final CustomerUpdateService customerUpdateService;

    public CustomerUpdateController(CustomerUpdateService customerUpdateService) {
        this.customerUpdateService = customerUpdateService;
    }

    @PutMapping(path = "{id}")
    public IdResponse getCustomers(
            @PathVariable("id") Long id,
            @RequestBody CustomerUpdateRequest customerUpdateRequest
    ) {
        return customerUpdateService.update(id, customerUpdateRequest);
    }

}
