package com.tuxpoli.customer;

public class CustomerDuplicateException extends RuntimeException {

    public CustomerDuplicateException(String message) {
        super(message);
    }
}
