package com.epam.esm.service.api;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService {

    /**
     * @param entity some tag
     * @return id tag
     */
    void create(Tag entity);


    /**
     * @return list of tags
     */
    List<Tag> findAll(int page, int size);

    /**
     * @param id id tag
     * @return tag
     */
    Tag findById(int id);

    /**
     * @param id id tag
     */
    void deleteById(int id);
}
