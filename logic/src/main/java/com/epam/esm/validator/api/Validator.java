package com.epam.esm.validator.api;

public interface Validator<T> {

    /**
     * @param entity some entity which need validation
     * @return boolean value valid or not valid entity
     */
    boolean isValid(T entity);
}
