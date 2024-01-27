package com.tuxpoli.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer yearOfBirth
) {

}
