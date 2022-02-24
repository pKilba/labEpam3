package com.epam.esm.security;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.User;
import com.epam.esm.repository.api.UserRepository;
import com.epam.esm.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userService) {
        this.userRepository = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByField("name", username)
                .orElseThrow(() -> new NotFoundEntityException("user not found"));
        return JwtUserFactory.create(user);
    }
}