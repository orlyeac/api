package com.tuxpoli.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "{id}")
    public CustomerResponse getCustomer(@PathVariable("id") Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public IdResponse createCustomer(@RequestBody CustomerCreateRequest customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping(path = "{id}")
    public IdResponse updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerUpdateRequest update
    ) {
        return customerService.updateCustomer(id, update);
    }

    @DeleteMapping(path = "{id}")
    public IdResponse deleteCustomer(@PathVariable("id") Long id) {
        return customerService.deleteCustomer(id);
    }
}
