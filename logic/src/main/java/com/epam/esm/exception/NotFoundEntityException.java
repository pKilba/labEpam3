package com.epam.esm.exception;

public class NotFoundEntityException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public NotFoundEntityException() {
    }

    public NotFoundEntityException(String message) {
        super(message);
    }
}
