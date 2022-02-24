package com.epam.esm.repository.api;

import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository  extends BaseRepository<Certificate>{

    /**
     *
     * @param name name certificate
     * @return certificate
     */
    Optional<Certificate> findByName(String name);

    /**
     *
     * @param tagNames name tag certificate
     * @param partInfo certificate name or description
     * @param page page for pagibation
     * @param size size for pagination
     * @return list certificates
     */
    List<Certificate> findAllWithSortingFiltering(
            List<String> tagNames, String partInfo,
            int page, int size);
}
