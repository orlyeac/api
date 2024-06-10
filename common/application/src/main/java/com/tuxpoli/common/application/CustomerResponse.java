package com.tuxpoli.common.application;

import com.tuxpoli.common.domain.LabourLink;

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
