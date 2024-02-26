package com.tuxpoli.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        LabourLink labourLink,
        String company
) {

}
