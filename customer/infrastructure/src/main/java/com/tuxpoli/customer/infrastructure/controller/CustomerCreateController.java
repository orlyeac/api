package com.tuxpoli.customer.infrastructure.controller;

import com.tuxpoli.common.application.IdResponse;
import com.tuxpoli.customer.application.service.CustomerCreateService;
import com.tuxpoli.customer.application.request.CustomerCreateRequest;
import com.tuxpoli.axiom.infrastructure.jwt.JWTUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerCreateController {

    private final CustomerCreateService customerCreateService;

    private final JWTUtility jwtUtility;

    public CustomerCreateController(
            CustomerCreateService customerCreateService,
            JWTUtility jwtUtility
    ) {
        this.customerCreateService = customerCreateService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(
            @RequestBody CustomerCreateRequest customerCreateRequest
    ) {
        IdResponse idResponse = customerCreateService.create(
                customerCreateRequest
        );
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtUtility.issueToken(
                                idResponse.id().toString(),
                                customerCreateRequest.email(),
                                "ROLE_USER")
                )
                .body(
                        idResponse
                );
    }

}
