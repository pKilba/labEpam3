package com.epam.esm.repository.api;

import com.epam.esm.model.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {

    List<Order> findAllByUserId(long userId, int page, int size);

}
