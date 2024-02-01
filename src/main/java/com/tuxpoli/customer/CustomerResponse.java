package com.tuxpoli.customer;

import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        Integer yearOfBirth,
        List<String> authority
) {

}
