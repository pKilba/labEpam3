package com.epam.esm.service.api;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.model.Order;


import java.util.List;

public interface OrderService {

    /**
     *
     * @param page page for pagination
     * @param size size for pagination
     * @return list orser
     */
    List<Order> findAll(int page, int size);

    /**
     *
     * @param userId user id
     * @param orderId order id
     * @return order
     * @throws NotFoundEntityException
     */
    Order findByUserId(long userId, long orderId) throws NotFoundEntityException;

    /**
     *
     * @param createOrderDto user id and certificate id
     * @return order
     * @throws NotFoundEntityException
     */
    Order create(CreateOrderDto createOrderDto) throws NotFoundEntityException;

    List<Order> findAllByUserId(long userId, int page, int size) throws NotFoundEntityException;
}
