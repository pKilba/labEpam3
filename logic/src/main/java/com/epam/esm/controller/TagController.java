package com.epam.esm.controller;

import com.epam.esm.link.TagLinkProvider;
import com.epam.esm.model.Tag;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.impl.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    public static final String SIZE = "size";
    public static final String PAGE = "page";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";

    private final TagService tagService;
    private final TagLinkProvider tagLinkProvider;
    private final RequestParametersValidator requestParametersValidator;

    @Autowired
    public TagController(TagService tagService, TagLinkProvider tagLinkProvider,
                         RequestParametersValidator requestParametersValidator) {


        this.tagService = tagService;
        this.tagLinkProvider = tagLinkProvider;
        this.requestParametersValidator = requestParametersValidator;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void create(@RequestBody Tag tag, HttpServletResponse response) {

        tagService.create(tag);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> findAll(@RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
                             @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size) {

        requestParametersValidator.paginationParamValid(page, size);

        List<Tag> tags = tagService.findAll(page, size);
        for (Tag tag : tags) {
            tagLinkProvider.provideLinks(tag);
        }
        return tags;
    }


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Tag findById(@PathVariable("id") int id) {
        requestParametersValidator.idParamValid(id);
        Tag tag = tagService.findById(id);
        tagLinkProvider.provideLinks(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteById(@PathVariable("id") int id) {
        requestParametersValidator.idParamValid(id);
        tagService.deleteById(id);
    }


}