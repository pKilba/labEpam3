package com.epam.esm.service.api;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    public List<User> findAll(int page, int size);
    User findById(long id) throws NotFoundEntityException;
    void addSpentMoney(long id, BigDecimal addedValue) throws NotFoundEntityException;
    public List <User> findByMostCost(int page,int size);
}
