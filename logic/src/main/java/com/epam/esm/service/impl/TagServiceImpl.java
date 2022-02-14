package com.epam.esm.service.impl;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.impl.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class TagServiceImpl implements TagService {

    private static final String TAG_EXIST = "Tag exist";
    private static final String TAG_NOT_FOUND = "Tag not found";
    private final TagValidator validator = new TagValidator();
    private final TagRepository tagDao;

    @Autowired
    public TagServiceImpl(TagRepository tagDao) {
        this.tagDao = tagDao;
    }

    public void create(Tag tag) {
        validator.isValid(tag);
        validateForExistTag(tag);
        tagDao.create(tag);
    }

    private void validateForExistTag(Tag tag) {
        if (tagDao.findByName(tag.getName()).isPresent()) {
            throw new DuplicateEntityException(TAG_EXIST);
        }
    }

    @Override
    public List<Tag> findAll(int page, int size) {
        return tagDao.findAllWithPagination(page, size);
    }

    @Override
    @Transactional
    public Tag findById(int id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException(TAG_NOT_FOUND);
        }
        return optionalTag.get();
    }


    @Override
    public void deleteById(int id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NotFoundEntityException(TAG_NOT_FOUND);
        }
        tagDao.deleteById(id);
    }
}
