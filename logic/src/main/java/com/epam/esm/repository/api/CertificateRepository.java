package com.epam.esm.repository.api;

import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository  extends BaseRepository<Certificate>{

    Optional<Certificate> findByName(String name);

    List<Certificate> findAllWithSortingFiltering(
            List<String> tagNames, String partInfo,
            int page, int size);
}
