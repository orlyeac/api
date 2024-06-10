package com.tuxpoli.axiom.infrastructure.exception;

import com.tuxpoli.common.domain.exception.ApiException;
import com.tuxpoli.common.domain.exception.DuplicateException;
import com.tuxpoli.common.domain.exception.NotFoundException;
import com.tuxpoli.common.domain.exception.WithoutChangeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiException> handleException(
            DuplicateException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiException> handleException(
            NotFoundException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiException> handleException(
            UsernameNotFoundException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WithoutChangeException.class)
    public ResponseEntity<ApiException> handleException(
            WithoutChangeException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiException> handleException(
            InsufficientAuthenticationException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        ), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiException> handleException(
            BadCredentialsException e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleException(
            Exception e,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(new ApiException(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
