package com.epam.esm.repository.api;

import com.epam.esm.model.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {

    /**
     *
     * @param userId user id
     * @param page page for pagination
     * @param size size for pagination
     * @return list order
     */
    List<Order> findAllByUserId(long userId, int page, int size);

}
