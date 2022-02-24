package com.epam.esm.repository;
import com.epam.esm.config.ConfigTest;
import com.epam.esm.model.Order;
import com.epam.esm.repository.api.OrderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = ConfigTest.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class OrderRepositoryTest {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final Order FIRST_ORDER = new Order(1, 1,1,Timestamp.valueOf("2022-01-25 18:00:16"), BigDecimal.ONE);
    private static final Order SECOND_ORDER = new Order(2, 1,1,Timestamp.valueOf("2022-01-25 18:00:16"), BigDecimal.ONE);

    @Autowired
    private  OrderRepository orderRepository;

    @Test
    public void testGetAllShouldGet() {
        List<Order> userList =
                orderRepository.findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);
        List<Long> tagName = userList.stream().map(Order::getId).collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(FIRST_ORDER.getId(), SECOND_ORDER.getId()), tagName);

    }

}
