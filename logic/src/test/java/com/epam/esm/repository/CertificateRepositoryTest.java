package com.epam.esm.repository;

import com.epam.esm.config.ConfigTest;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.api.CertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = ConfigTest.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class CertificateRepositoryTest {

    private static final Certificate FIRST_CERTIFICATE = new Certificate(1, "test1",
            "description", new BigDecimal(1.10), Timestamp.valueOf("2022-01-25 18:00:16"),
            Timestamp.valueOf("2022-01-25 18:00:16"), 5);

    private static final Certificate SECOND_CERTIFICATE = new Certificate(2, "test2",
            "description", new BigDecimal(1.10), Timestamp.valueOf("2022-01-25 18:00:16"),
            Timestamp.valueOf("2022-01-25 18:00:16"), 5);

    private static final Tag FIRST_TAG = new Tag(1, "tag 1");
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    static {
        FIRST_CERTIFICATE.setTagList(new ArrayList<>(Arrays.asList(FIRST_TAG)));
    }

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    public void testCreateCertificateShouldCreate() {
        Certificate createdCertificate = certificateRepository.findByName(FIRST_CERTIFICATE.getName()).get();
        Assertions.assertNotNull(createdCertificate);
    }

    @Test
    public void testGetAllShouldGet() {
        List<Certificate> certificateList =
                certificateRepository.findAllWithPagination(DEFAULT_PAGE, DEFAULT_SIZE);
        List<String> certificateName = certificateList.stream().map(Certificate::getName).collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(FIRST_CERTIFICATE.getName(), SECOND_CERTIFICATE.getName()), certificateName);
    }

    @Test
    public void testGetAllWithSortingFilteringShouldGetSortedCertificates() {

        List<Certificate> certificateList =
                certificateRepository.findAllWithSortingFiltering(
                        null, null, DEFAULT_PAGE, DEFAULT_SIZE);
        List<String> certificateName = certificateList.stream().map(Certificate::getName).collect(Collectors.toList());
        Assertions.assertEquals(Arrays.asList(FIRST_CERTIFICATE.getName(), SECOND_CERTIFICATE.getName()), certificateName);
    }

    @Test
    public void testFindByIdShouldFind() {
        Optional<Certificate> giftCertificate = certificateRepository.findById(
                FIRST_CERTIFICATE.getId());
        Assertions.assertEquals(FIRST_CERTIFICATE.getName(), giftCertificate.get().getName());
    }

    @Test
    public void testUpdateByIdShouldUpdate() {
        String savedName = FIRST_CERTIFICATE.getName();
        FIRST_CERTIFICATE.setName("new name");
        Certificate updatedCertificate = certificateRepository.update(FIRST_CERTIFICATE);
        Assertions.assertEquals(updatedCertificate.getName(), "new name");
        FIRST_CERTIFICATE.setName(savedName);
    }

    @Test
    public void testDeleteByIdShouldDelete() {
        certificateRepository.deleteById(SECOND_CERTIFICATE.getId());
        boolean isExist = certificateRepository.findById(SECOND_CERTIFICATE.getId()).isPresent();
        Assertions.assertFalse(isExist);
    }
}
