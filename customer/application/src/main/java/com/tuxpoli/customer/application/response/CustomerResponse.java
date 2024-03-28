package com.tuxpoli.customer.application.response;

import com.tuxpoli.customer.domain.model.LabourLink;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        LabourLink labourLink,
        String company,
        Boolean active,
        String authority,
        LocalDateTime createdAt
) {

}
