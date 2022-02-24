package com.epam.esm.repository.api;

import com.epam.esm.model.Tag;

import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag> {

    /**
     *
     * @param name name tag
     * @return tag
     */
    Optional<Tag> findByName(String name);

}
