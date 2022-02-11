package com.epam.esm.exception;

import org.springframework.stereotype.Component;

@Component
public class ValidationException extends RuntimeException{
    static final long serialVersionUID = 1L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }


}
