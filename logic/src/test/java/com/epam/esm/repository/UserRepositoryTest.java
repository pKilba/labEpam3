package com.epam.esm.repository;

import com.epam.esm.config.ConfigTest;
import com.epam.esm.model.User;
import com.epam.esm.repository.api.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = ConfigTest.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class UserRepositoryTest {

    private static final User USER = new User(1,"user", BigDecimal.ONE);
    private static final User SECOND_USER = new User(2,"user", BigDecimal.ONE);

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateTagShouldCreate() {
        User createdUser = userRepository.findById(USER.getId()).get();
        Assertions.assertNotNull(createdUser);
    }

    @Test
    public void testGetAllShouldGet() {
        List<User> userList =
                userRepository.findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);
        List<String> tagName = userList.stream().map(User::getName).collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(USER.getName(), SECOND_USER.getName()), tagName);
    }
}
