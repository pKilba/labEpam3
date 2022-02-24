package com.epam.esm.security;

import com.epam.esm.security.jwt.JwtConfigurer;
import com.epam.esm.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.epam.esm.util.Endpoints.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers(LOGIN_ENDPOINT, SIGNUP_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, CERTIFICATE_ENDPOINT).permitAll()
                .antMatchers(ORDERS_ENDPOINT, USERS_ENDPOINT).hasRole(USER)
                .antMatchers(HttpMethod.GET, TAGS_ENDPOINT).permitAll()
                .antMatchers(CERTIFICATE_ENDPOINT).hasRole(ADMIN)
                .antMatchers(TAGS_ENDPOINT).hasRole(ADMIN)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}