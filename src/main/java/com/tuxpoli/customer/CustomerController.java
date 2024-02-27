package com.tuxpoli.customer;

import com.tuxpoli.jwt.JWTUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtility jwtUtility;

    public CustomerController(CustomerService customerService, JWTUtility jwtUtility) {
        this.customerService = customerService;
        this.jwtUtility = jwtUtility;
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
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateRequest customer) {
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtUtility.issueToken(customer.email(), "ROLE_USER")
                )
                .body(customerService.createCustomer(customer));
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
