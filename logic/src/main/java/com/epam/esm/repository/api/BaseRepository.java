package com.epam.esm.repository.api;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    /**
     * @param entity
     * @return entity
     */
    T create(T entity);

    /**
     * @param page page for pagination
     * @param size page for size
     * @return list entity
     */
    List<T> findAllWithPagination(int page, int size);

    /**
     * @param id id entity
     * @return entity
     */

    Optional<T> findById(long id);


    /**
     *
     * @param entity
     * @return entity
     */
    T update(T entity);

    void deleteById(int id);

    Optional<T> findByField(String columnName, Object value);

}
