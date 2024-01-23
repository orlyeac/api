package com.tuxpoli.customer;

public class CustomerWithoutChangeException extends RuntimeException {

    public CustomerWithoutChangeException(String message) {
        super(message);
    }
}
