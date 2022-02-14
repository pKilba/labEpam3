package com.epam.esm.service;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.Order;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
public class OrderServiceTest {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    @Autowired
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepositoryImpl orderRepository;


    @Test
    public void testFindAllShouldFindAll() {
        when(orderRepository.findAllWithPagination(DEFAULT_PAGE,DEFAULT_SIZE)).thenReturn(new ArrayList<>());
        orderService.findAll(DEFAULT_PAGE,DEFAULT_SIZE);
        verify(orderRepository).findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);
    }

//    @Test
//    public void testCreateShouldCreateWhenValidAndNotExist() {
//        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
//        tagService.create(Order);
//        verify(tagDao).create(TAG);
//    }

    @Test
    void testCreateOrder() {
        CreateOrderDto order = new CreateOrderDto(1, 1);
        when(orderService.create(order)).thenReturn(FIRST_ORDER);
        Assertions.assertThrows(NotFoundEntityException.class, () -> {
            System.out.println(orderService.findByUserId(1, 1));
        });
//        verify(orderRepository).findAllByUserId(1, 0, 1);
    }

    @Test
    void testFind() {

    }

//    @Test(expected = NotFoundEntityException.class)
//    public void testGetByUserIdShouldThrowsInvalidParametersExceptionWhenOrderNotFound() {
//        when(orderRepository.findById(1)).thenReturn(Optional.empty());
//        orderService.findByUserId(1, 1);
//    }


}
