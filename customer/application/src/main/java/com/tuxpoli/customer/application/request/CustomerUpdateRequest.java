package com.tuxpoli.customer.application.request;

import com.tuxpoli.common.domain.LabourLink;

public record CustomerUpdateRequest(
        String name,
        String email,
        LabourLink labourLink,
        String company
) {

}
