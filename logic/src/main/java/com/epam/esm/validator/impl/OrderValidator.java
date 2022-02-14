package com.epam.esm.validator.impl;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.exception.NotValidEntityException;
import com.epam.esm.validator.api.Validator;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator implements Validator<CreateOrderDto> {
    private static final int MIN_LENGTH = 0;
    private static final int MAX_LENGTH = 15000;

    @Override
    public boolean isValid(CreateOrderDto entity) {

        validNumber(entity.getUserId());
        validNumber(entity.getCertificateId());

        return true;
    }

    public boolean validNumber(long number) {
        if (number < MIN_LENGTH || number > MAX_LENGTH) {
            throw new NotValidEntityException("Not valid order");
        }
        return true;
    }


}
