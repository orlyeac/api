package com.tuxpoli.customer;

public record CustomerCreateRequest(
        String name,
        String email,
        String password,
        Integer yearOfBirth
) {

}
