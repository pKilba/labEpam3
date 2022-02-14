package com.epam.esm.validator.impl;

import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class RequestParametersValidator {

    private final int NUMBER_MIN = 0;

    public void paginationParamValid(int page, int size) {
        if (page < NUMBER_MIN || size <= NUMBER_MIN) {
            throw new ValidationException("Exception with pagination param");
        }
    }

    public void idParamValid(long id) {
        if (id < NUMBER_MIN || id >= Integer.MAX_VALUE) {
            throw new ValidationException("Exception with id param");
        }
    }
}
