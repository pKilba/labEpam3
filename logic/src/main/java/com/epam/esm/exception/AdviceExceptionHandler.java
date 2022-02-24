package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceExceptionHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ExceptionEntity> handleDuplicateEntityException(DuplicateEntityException e) {
        return new ResponseEntity(new ExceptionEntity(HttpStatus.CONFLICT.toString(), e.getMessage(), 40901), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotValidEntityException.class)
    public ResponseEntity<ExceptionEntity> handleNotValidEntityException(NotValidEntityException e) {
        return new ResponseEntity<>(new ExceptionEntity(HttpStatus.CONFLICT.toString(), e.getMessage(), 40000), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ExceptionEntity> handleNotFoundEntityException(NotFoundEntityException e) {
        return new ResponseEntity<>(new ExceptionEntity(HttpStatus.NOT_FOUND.toString(), e.getMessage(), 40401), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionEntity> handleNotValidParamPaginationException(ValidationException e) {
        return new ResponseEntity<>(new ExceptionEntity(HttpStatus.CONFLICT.toString(), e.getMessage(), 30434), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionEntity> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionEntity(HttpStatus.NOT_FOUND.toString(), "EXCEPTION", 5000), HttpStatus.EXPECTATION_FAILED);
    }

}
