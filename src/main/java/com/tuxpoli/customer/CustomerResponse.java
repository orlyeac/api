package com.tuxpoli.customer;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        Integer yearOfBirth
) {

}
