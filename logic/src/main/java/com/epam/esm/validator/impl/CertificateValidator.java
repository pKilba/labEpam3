package com.epam.esm.validator.impl;

import com.epam.esm.exception.NotValidEntityException;
import com.epam.esm.model.Certificate;
import com.epam.esm.validator.api.Validator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CertificateValidator implements Validator<Certificate> {

    private static final String NOT_VALID_CERTIFICATE = "Not valid certificate";
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 564;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 64;
    private static final BigDecimal PRICE_MIN_VALUE = BigDecimal.ONE;
    private static final BigDecimal PRICE_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
    private static final int DURATION_MIN_VALUE = 1;
    private static final int DURATION_MAX_VALUE = 256;

    @Override
    public boolean isValid(Certificate entity) {
        if (isNameValid(entity.getName())
                && isDescriptionValid(entity.getDescription())
                && isPriceValid(entity.getPrice())
                && isDurationValid(entity.getDuration())) {
            return true;
        } else {
            throw new NotValidEntityException(NOT_VALID_CERTIFICATE);
        }
    }

    public boolean isDurationValid(int duration) {
        return duration >= DURATION_MIN_VALUE
                && duration <= DURATION_MAX_VALUE;
    }

    public boolean isPriceValid(BigDecimal price) {
        return price != null
                && price.compareTo(PRICE_MIN_VALUE) > 0
                && price.compareTo(PRICE_MAX_VALUE) < 0;
    }

    public boolean isDescriptionValid(String description) {
        return description != null
                && description.length() >= DESCRIPTION_MIN_LENGTH
                && description.length() <= DESCRIPTION_MAX_LENGTH;
    }

    public boolean isNameValid(String name) {
        return name != null
                && name.length() >= NAME_MIN_LENGTH
                && name.length() <= NAME_MAX_LENGTH;
    }

}
