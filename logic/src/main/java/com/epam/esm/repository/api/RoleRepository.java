package com.epam.esm.repository.api;

import com.epam.esm.model.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
}
