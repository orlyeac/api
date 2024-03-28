package com.tuxpoli.customer.domain.exception;

public class WithoutChangeException extends RuntimeException {

    public WithoutChangeException(String message) {
        super(message);
    }
}
