package com.epam.esm.repository.api;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    T create(T entity);

    List<T> findAllWithPagination(int page, int size);

    Optional<T> findById(long id);

    Optional<T> findByField(String columnName, Object value);

    T update(T entity);

    void deleteById(int id);
}
