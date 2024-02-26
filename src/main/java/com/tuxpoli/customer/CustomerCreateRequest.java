package com.tuxpoli.customer;

public record CustomerCreateRequest(
        String name,
        String email,
        String password,
        LabourLink labourLink,
        String company
) {

}
