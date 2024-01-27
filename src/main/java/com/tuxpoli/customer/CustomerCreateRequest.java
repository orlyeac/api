package com.tuxpoli.customer;

public record CustomerCreateRequest(
        String name,
        String email,
        Integer yearOfBirth
) {

}
