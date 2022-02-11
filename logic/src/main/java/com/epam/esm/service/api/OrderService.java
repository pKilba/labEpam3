package com.epam.esm.service.api;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.CreateOrderDto;
import com.epam.esm.model.Order;


import java.util.List;

public interface OrderService {
    List<Order> findAll(int page, int size);

    Order findByUserId(long userId, long orderId) throws NotFoundEntityException;

    Order create(CreateOrderDto createOrderDto) throws NotFoundEntityException;

    List<Order> findAllByUserId(long userId, int page, int size) throws NotFoundEntityException;
}
