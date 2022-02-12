package com.epam.esm.service.api;

import com.epam.esm.model.Certificate;

import java.util.List;

public interface CertificateService {

    /**
     * @param entity certificate and list tags
     * @return id certificate
     */
    void create(Certificate entity);

    /**
     * @return list of certificates
     */
    List<Certificate> findAll(List<String> tagNames,
                              String partInfo,
                              int page, int size);

    /**
     * @param id id certificate
     * @return certificate
     */
    Certificate findById(int id);


    /**
     * @param id id certificate
     */
    void deleteById(int id);

    /**
     * @param id             id certificate
     * @param сertificateDto certificate and set tag
     * @return certificate and set tag
     */
    Certificate updateById(int id, Certificate сertificateDto);


}
