package com.tuxpoli.customer;

public class CustomerUpdateWithoutChangeException extends RuntimeException {

    public CustomerUpdateWithoutChangeException(String message) {
        super(message);
    }
}
