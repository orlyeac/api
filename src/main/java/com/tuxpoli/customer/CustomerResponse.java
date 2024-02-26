package com.tuxpoli.customer;

import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        LabourLink labourLink,
        String company,
        List<String> authority
) {

}
