package com.epam.esm.dto;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;

import java.util.HashSet;
import java.util.Set;

public class CertificateDto {

    private Certificate certificate;
    private Set<Tag> tags = new HashSet<>();

    public CertificateDto(Certificate certificate, Set<Tag> tags) {
        this.certificate = certificate;
        this.tags = tags;
    }

    public CertificateDto() {
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
