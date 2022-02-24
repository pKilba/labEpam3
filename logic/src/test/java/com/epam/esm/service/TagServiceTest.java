package com.epam.esm.service;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.NotValidEntityException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.api.Validator;
import com.epam.esm.validator.impl.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
public class TagServiceTest {

    @MockBean
    private TagRepositoryImpl tagDao;

    @Autowired
    private TagServiceImpl tagService;

    private Validator<Tag> tagValidator;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    private static final Tag TAG = new Tag("testTag");

    @BeforeEach
    public void initMethod() {
        tagValidator = Mockito.mock(TagValidator.class);
    }

    @Test
    public void testFindAllShouldFindAll() {
        tagService.findAll(DEFAULT_PAGE,DEFAULT_SIZE);
        verify(tagDao).findAllWithPagination(DEFAULT_PAGE,DEFAULT_SIZE);
    }

    @Test
    public void testCreateShouldCreateWhenValidAndNotExist() {
        when(tagValidator.isValid(any())).thenReturn(true);
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        tagService.create(TAG);
        verify(tagDao).create(TAG);
    }

    @Test
    public void testCreateShouldThrowsDuplicateEntityExceptionWhenExist() {
        when(tagValidator.isValid(any())).thenReturn(true);
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(TAG));
        assertThrows(DuplicateEntityException.class, () -> tagService.create(TAG));
    }

    @Test
    public void testDeleteByIdShouldDeletedWhenFound() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(TAG));
        tagService.deleteById(1);
        verify(tagDao).deleteById(1);
    }
    @Test
    public void testCreateShouldThrowsNotValidTagExceptionWhenTagNotCorrect() {
        when(tagValidator.isValid(any())).thenReturn(false);
        assertThrows(NotValidEntityException.class, () -> tagService.create(new Tag()));
    }

    @Test
    public void testFindByIdShouldThrowsNotFoundEntityExceptionWhenNotFound() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> tagService.findById(-1));
    }

    @Test
    public void testFindByIdShouldGetWhenFound() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(TAG));
        tagService.findById(1);
        verify(tagDao).findById(1);
    }

}
