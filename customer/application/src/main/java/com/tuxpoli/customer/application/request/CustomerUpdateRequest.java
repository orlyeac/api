package com.tuxpoli.customer.application.request;

import com.tuxpoli.customer.domain.model.LabourLink;

public record CustomerUpdateRequest(
        String name,
        String email,
        LabourLink labourLink,
        String company
) {

}
