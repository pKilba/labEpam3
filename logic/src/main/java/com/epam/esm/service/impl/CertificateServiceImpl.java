package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.api.CertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.CertificateService;
import com.epam.esm.service.api.TagService;
import com.epam.esm.validator.impl.CertificateValidator;
import com.epam.esm.validator.impl.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class CertificateServiceImpl implements CertificateService {


    public static final String TAG_NAME = "tag_name";
    public static final String PART_NAME = "part_name";
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String SIZE = "size";
    public static final String PAGE = "page";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";


    private static final String CERTIFICATE_EXIST = "Certificate exist";
    private static final String CERTIFICATE_NOT_EXIST = "Certificate not exist";
    private static final String CERTIFICATE_NOT_FOUND = "Certificate not found";

    private final CertificateRepository certificateDao;
    private final TagRepository tagDao;
    private final TagValidator tagValidator;
    private final CertificateValidator certificateValidator;
    private final TagService tagService;

    //todo interface поставить сюда


    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateDao,
                                  TagRepository tagDao,
                                  TagValidator tagValidator,
                                  CertificateValidator certificateValidator,
                                  TagService tagService
                                  ) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
        this.certificateValidator = certificateValidator;
        this.tagService = tagService;
    }




    ///todo не создается если тег уже существует разобраться !!!!!

    @Override
    @Transactional
    public int create(Certificate certificate) {
       // Certificate certificate = certificateDto.getCertificate();
        Set<Tag> tags = new HashSet();

        List<Tag> tagsToAdd = new ArrayList<>();
        if (certificate.getTagList() != null) {
            for (Tag tag : certificate.getTagList()) {
                Optional<Tag> tagOptional = tagDao.findByName(tag.getName());
                if (tagOptional.isPresent()) {
                    tagsToAdd.add(tagOptional.get());
                } else {
                    tagsToAdd.add(tag);
                }
            }
        }
        certificate.setTagList(tagsToAdd);
        //tags.addAll(certificate.getTagList());
        //validateForExistTag(tags);
        certificateDao.create(certificate);


        //todo check
       // certificateWithTag.create(certificate, tags);

        return 1;
    }

    private void validateForExistTag(Set<Tag> tags) {

        for (Tag tag : tags) {
            if (!tagDao.findByName(tag.getName()).isPresent()) {
                tagDao.create(tag);
            }
        }
    }

    private void validateForExistCertificates(Certificate certificate) {
        if (certificateDao.findByName(certificate.getName()).isPresent()) {
            throw new DuplicateEntityException(CERTIFICATE_EXIST);
        }
    }


    @Override
    public List<Certificate> findAll(
            List<String> tagNames,
            String partInfo,
            int page, int size) {
        return certificateDao.findAllWithSortingFiltering(tagNames, partInfo, page, size);
    }

    @Override
    public Certificate findById(int id) {
        Optional<Certificate> certificateOptional = certificateDao.findById(id);
        if (!certificateOptional.isPresent()) {
            throw new NotFoundEntityException("certificate not found");
        }
        return certificateOptional.get();
    }

    

    @Override
    public void deleteById(long id) {
        Optional<Certificate> certificateOptional = certificateDao.findById(id);
        if (!certificateOptional.isPresent()) {
            throw new NotFoundEntityException(CERTIFICATE_NOT_FOUND);
        }
        certificateDao.deleteById(id);
    }



    //todo
    @Transactional
    public CertificateDto updateById(int id, CertificateDto certificateDto) {

//        Certificate certificate = certificateDto.getCertificate();
//        Set<Tag> tags = new HashSet();
//        tags.addAll(certificateDto.getTags());
//        if (!certificateDao.findById(id).isPresent()) {
//            throw new NotFoundEntityException(CERTIFICATE_NOT_EXIST);
//        }
//
//        if (certificate != null) {
//            certificateDao.updateCertificateById(id, certificateDto);
//        }
//
//        if (tags != null) {
//            for (Tag tag : tags) {
//                if (!tagDao.findByName(tag.getName()).isPresent()) {
//                    tagService.create(tag);
//                }
//            }
//            certificateWithTag.create(findById(id), tags);
//        }
//        return certificateDto;
        return null;
    }


}
