package com.epam.esm.service;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.impl.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


//    @Test
//    public void testFindAllShouldFindAll() {
//        orderService.findAll(DEFAULT_PAGE,DEFAULT_SIZE);
//        verify(orderRepository).findAllWithPagination(DEFAULT_PAGE,DEFAULT_SIZE);
//    }

//    @Test(expected = NotFoundEntityException.class)
//    public void testGetByUserIdShouldThrowsInvalidParametersExceptionWhenOrderNotFound() {
//        when(orderRepository.findById(1)).thenReturn(Optional.empty());
//        orderService.findByUserId(1, 1);
//    }


}
