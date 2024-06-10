package com.tuxpoli.customer.application.request;

import com.tuxpoli.common.domain.LabourLink;

public record CustomerCreateRequest(
        String name,
        String email,
        String password,
        LabourLink labourLink,
        String company
) {

}
