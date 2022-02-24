package com.epam.esm.controller;
import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.model.User;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto requestDto) {
        String login = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
        User user = userService.findByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + login + " not found");
        }
        String token = jwtTokenProvider.createToken(login,user.getRoles());
        return new AuthenticationResponseDto(login, token);
    }
}