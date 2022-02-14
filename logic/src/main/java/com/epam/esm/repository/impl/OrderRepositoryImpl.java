package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.repository.api.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Order.class);
    }

    public List<Order> findAllByUserId(long userId, int page, int size) {
        CriteriaQuery<Order> query = buildGetAllQuery(userId);
        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    private CriteriaQuery<Order> buildGetAllQuery(long userId) {
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        Predicate predicate = builder.equal(root.get("user"), userId);
        query.where(predicate);
        return query;
    }
}
