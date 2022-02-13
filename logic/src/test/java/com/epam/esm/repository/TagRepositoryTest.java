package com.epam.esm.repository;

import com.epam.esm.config.ConfigTest;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.api.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = ConfigTest.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class TagRepositoryTest {


    @Autowired
    private TagRepository tagRepository;

    private static final Tag FIRST_TAG = new Tag(1, "test1");
    private static final Tag SECOND_TAG = new Tag(2, "test2");

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Test
    public void testCreateTagShouldCreate() {
        Tag createdTag = tagRepository.findByName(FIRST_TAG.getName()).get();
        Assertions.assertNotNull(createdTag);
    }
    @Test
    public void testGetAllShouldGet() {
        List<Tag> tagList =
                tagRepository.findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);
        List<String> tagName = tagList.stream().map(Tag::getName).collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(FIRST_TAG.getName(), SECOND_TAG.getName()), tagName);

    }
    @Test
    public void testFindByIdShouldFind() {
        Optional<Tag> tag = tagRepository.findById(
                FIRST_TAG.getId());
        Assertions.assertEquals(FIRST_TAG.getName(), tag.get().getName());
    }


    @Test
    public void testDeleteByIdShouldDelete() {
        tagRepository.deleteById(SECOND_TAG.getId());

        boolean isExist = tagRepository.findById(SECOND_TAG.getId()).isPresent();
        Assertions.assertFalse(isExist);
    }
}
