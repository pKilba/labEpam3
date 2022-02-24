package com.epam.esm.service;

import com.epam.esm.model.User;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
public class UserServiceTest {
    private static final long ID = 1;
    private static final User USER = new User("user", BigDecimal.ONE);
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @MockBean
    private UserRepositoryImpl userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testGetByIdShouldGetWhenFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));
        userService.findById(1);
        verify(userRepository).findById(1);
    }

    @Test
    public void testGetAllShouldThrowsInvalidParametersExceptionWhenParamsInvalid() {
        userService.findAll(DEFAULT_PAGE, DEFAULT_SIZE);
        verify(userRepository).findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);
    }

}
