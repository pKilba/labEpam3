package com.epam.esm.service;

import com.epam.esm.config.ConfigTest;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.api.CertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.service.api.CertificateService;
import com.epam.esm.service.api.TagService;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.validator.api.Validator;
import com.epam.esm.validator.impl.CertificateValidator;
import com.epam.esm.validator.impl.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;



@SpringBootTest

public class CertificateServiceTest {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @MockBean
    private CertificateRepositoryImpl certificateDao;
    private Validator<Certificate> certificateValidator;
    @Autowired
    private CertificateServiceImpl certificateService;
    private TagRepository tagDao;
    private Validator<Tag> tagValidator;
    private TagService tagService;


    private static final Certificate CERTIFICATE = new Certificate(1,"testTest",
            "description", BigDecimal.TEN, new Timestamp(System.currentTimeMillis()),
            new Timestamp(System.currentTimeMillis()), 3);


    @BeforeEach
    public void initMethod() {
     //   certificateDao = Mockito.mock(CertificateRepositoryImpl.class);
        certificateValidator = Mockito.mock(CertificateValidator.class);
      //  tagDao = Mockito.mock(TagRepository.class);
        tagValidator = Mockito.mock(TagValidator.class);
      //  tagService = Mockito.mock(TagService.class);
      //  certificateService = Mockito.mock(CertificateServiceImpl.class);
    }

    @Test
    public void testDeleteByIdShouldThrowsNoSuchEntityExceptionWhenNotFound() {
        Assertions.assertThrows(Exception.class, () -> {
            when(certificateDao.findById(anyLong())).thenReturn(Optional.empty());
            certificateService.deleteById(10);
        });
    }




    @Test
    public void testGetByIdShouldGetWhenFound() {
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(CERTIFICATE));
       certificateService.findById(1);
        verify(certificateDao).findById(1L);
    }


    @Test
    public void testUpdateByIdShouldUpdateWhenFound() {
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(CERTIFICATE));
        certificateService.updateById(1, CERTIFICATE);
        verify(certificateDao).update(CERTIFICATE);
    }


    @Test
    public void testUpdateByIdShouldThrowsNoSuchEntityExceptionWhenNotFound() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> {
            when(certificateDao.findById(anyLong())).thenReturn(Optional.empty());
            certificateService.updateById(1, CERTIFICATE);
        });
    }

    @Test
    public void testDeleteByIdShouldDeleteWhenFound() {
        when(certificateDao.findById(anyLong())).thenReturn(Optional.of(CERTIFICATE));
        certificateService.deleteById(1);
        verify(certificateDao).deleteById(1);
    }

    }









