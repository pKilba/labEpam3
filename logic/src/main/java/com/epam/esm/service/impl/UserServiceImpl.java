package com.epam.esm.service.impl;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.Role;
import com.epam.esm.model.Status;
import com.epam.esm.model.User;
import com.epam.esm.repository.api.RoleRepository;
import com.epam.esm.repository.api.UserRepository;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userDao,RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {
        this.userDao = userDao;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
    }
    public List<User> findAll(int page, int size) {

        return userDao.findAllWithPagination(page, size);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
      return userDao.findByField("name", username)
                .orElseThrow(() -> new NotFoundEntityException("not "));
    }

    @Transactional
    public User register(User userDto) {
        User user = userDto;
        if (userDao.findByField("name", user.getName()).isPresent()) {
            throw new DuplicateEntityException("user.exist");
        }
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundEntityException("role.not.found"));
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setRoles(userRoles);
        return userDao.create(user);
    }
    public void checkAccess(HttpServletRequest httpServletRequest, long userId) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String login = jwtTokenProvider.getUsername(token);
        List<String> roles = jwtTokenProvider.getRoles(token);
        User userDto = userDao.findByField("name",login).get(); //  userService.findByUsername(login);
        if ((userDto.getId() != userId) && !roles.contains("ROLE_ADMIN")) {
            throw new AuthorizationServiceException("exception.no.access");
        }
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
