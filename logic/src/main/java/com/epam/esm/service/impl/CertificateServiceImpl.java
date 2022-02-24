package com.epam.esm.service.impl;

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
import java.util.List;
import java.util.Optional;

@Component
public class CertificateServiceImpl implements CertificateService {

    private static final String CERTIFICATE_EXIST = "Certificate exist";
    private static final String CERTIFICATE_NOT_EXIST = "Certificate not exist";
    private static final String CERTIFICATE_NOT_FOUND = "Certificate not found";
    private final CertificateRepository certificateDao;
    private final TagRepository tagDao;
    private final TagValidator tagValidator;
    private final CertificateValidator certificateValidator;
    private final TagService tagService;

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

    @Override
    @Transactional
    public void create(Certificate certificate) {
        List<Tag> tagsToAdd = new ArrayList<>();
        certificateValidator.isValid(certificate);
        validateForExistCertificates(certificate);
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
        certificateDao.create(certificate);
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
    public void deleteById(int id) {
        Optional<Certificate> certificateOptional = certificateDao.findById(id);
        if (!certificateOptional.isPresent()) {
            throw new NotFoundEntityException(CERTIFICATE_NOT_FOUND);
        }
        certificateDao.deleteById(id);
    }


    @Transactional
    public Certificate updateById(int id, Certificate certificate) {
        Certificate presentCertificate = certificateDao.findById(id).orElseThrow(() -> new NotFoundEntityException(CERTIFICATE_NOT_EXIST));
        //todo вынести в отдельную логику ??
        if (certificate.getName() != presentCertificate.getName() && certificate.getName() != null) {
            presentCertificate.setName(certificate.getName());
        }
        if (certificate.getDescription() != presentCertificate.getDescription() && certificate.getDescription() != null) {
            presentCertificate.setDescription(certificate.getDescription());
        }

        if (certificate.getPrice() != presentCertificate.getPrice() && certificate.getPrice() != null) {
            presentCertificate.setPrice(certificate.getPrice());
        }

        if (certificate.getDuration() != presentCertificate.getDuration() && certificate.getDuration() != 0) {
            presentCertificate.setDuration(certificate.getDuration());
        }
        if (certificate.getTagList() != null) {
            List<Tag> tags = certificate.getTagList();
            presentCertificate.setTagList(addTags(tags));
        }

        return certificateDao.update(presentCertificate);
    }

    private List<Tag> addTags(List<Tag> tags) {
        List<Tag> addedTags = new ArrayList<>();
        for (Tag tag : tags) {
            Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
            Tag savedTag = optionalTag.orElseGet(() -> tagDao.create(tag));
            addedTags.add(savedTag);
        }
        return addedTags;
    }

}
