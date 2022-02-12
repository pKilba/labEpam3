package com.epam.esm.service.impl;


import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.User;
import com.epam.esm.repository.api.UserRepository;
import com.epam.esm.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {


    private final UserRepository userDao;


    @Autowired
    public UserServiceImpl(UserRepository userDao) {
        this.userDao = userDao;
    }


    public List<User> findAll(int page, int size) {

        return userDao.findAllWithPagination(page, size);
    }

    @Transactional
    public User findById(long id) throws NotFoundEntityException {
        return userDao.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found user"));
    }

    @Override
    public void addSpentMoney(long id, BigDecimal addedValue) throws NotFoundEntityException {
        User user = userDao.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found user"));
        user.setSpentMoney(user.getSpentMoney().add(addedValue));
        userDao.update(user);
    }

    @Override
    public List<User> findByMostCost(int page, int size) {
        List<User> user = userDao.findAllWithPagination(page, size).stream()
                .sorted(Comparator.comparing(User::getSpentMoney)
                        .thenComparing(User::getSpentMoney))
                .collect(Collectors.toList());
        Collections.reverse(user);
        return user;
    }
}
