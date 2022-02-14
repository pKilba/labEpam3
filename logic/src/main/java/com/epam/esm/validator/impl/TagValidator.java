package com.epam.esm.validator.impl;

import com.epam.esm.exception.NotValidEntityException;
import com.epam.esm.model.Tag;
import com.epam.esm.validator.api.Validator;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<Tag> {

    private static final String NOT_VALID_TAG = "Not valid tag";
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 564;

    @Override
    public boolean isValid(Tag tag) {
        String name = tag.getName();
        if (name == null) {
            throw new NotValidEntityException(NOT_VALID_TAG);
        }
        name = name.trim();
        if (name.length() > NAME_MIN_LENGTH
                && name.length() < NAME_MAX_LENGTH) {
            return true;
        } else {
            throw new NotValidEntityException(NOT_VALID_TAG);
        }
    }

}

