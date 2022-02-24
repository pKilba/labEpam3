package com.epam.esm.service.api;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.User;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User register(User userDto);

    void checkAccess(HttpServletRequest httpServletRequest, long userId);

    /**
     * @param page page for pagination
     * @param size size for pagination
     * @return list users
     */
    List<User> findAll(int page, int size);

    /**
     * @param id id user
     * @return user
     * @throws NotFoundEntityException
     */
    User findById(long id) throws NotFoundEntityException;

    /**
     * @param id         id user
     * @param addedValue added certificate cost
     * @throws NotFoundEntityException
     */
    void addSpentMoney(long id, BigDecimal addedValue) throws NotFoundEntityException;

    List<User> findByMostCost(int page, int size);
}
