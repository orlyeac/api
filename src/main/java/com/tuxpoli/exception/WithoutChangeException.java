package com.tuxpoli.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WithoutChangeException extends RuntimeException {

    public WithoutChangeException(String message) {
        super(message);
    }
}
