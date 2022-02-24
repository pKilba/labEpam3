package com.epam.esm.repository.impl;

import com.epam.esm.model.Role;
import com.epam.esm.repository.api.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return findByField("name", name);
    }
}