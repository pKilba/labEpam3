package com.epam.esm.repository.impl;

import com.epam.esm.util.UtilBuilderQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class AbstractRepository<T> {

    @PersistenceContext
    protected final EntityManager entityManager;
    protected final CriteriaBuilder builder;
    protected final UtilBuilderQuery utilBuilderQuery;
    protected final Class<T> entityClass;

    public AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.utilBuilderQuery = new UtilBuilderQuery(this.builder);
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Optional<T> findById(long id) {
        return findByField("id", id);
    }

    public Optional<T> findByField(String columnName, Object value) {
        CriteriaQuery<T> entityQuery = builder.createQuery(entityClass);
        Root<T> root = entityQuery.from(entityClass);
        entityQuery.select(root);
        Predicate fieldPredicate = builder.equal(root.get(columnName), value);
        entityQuery.where(fieldPredicate);
        TypedQuery<T> typedQuery = entityManager.createQuery(entityQuery);
        return utilBuilderQuery.getOptionalQueryResult(typedQuery);
    }

    public T update(T entity) {

        return entityManager.merge(entity);
    }

    public List<T> findAllWithPagination(int page, int size) {
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public List<T> findAll() {
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return entityManager.createQuery(query)
                .getResultList();
    }


    public void deleteById(int id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

}
