package com.epam.esm.repository.impl;

import com.epam.esm.model.User;
import com.epam.esm.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

}
