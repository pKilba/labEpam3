package com.epam.esm.link;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.model.Certificate;
import org.springframework.stereotype.Component;

@Component
public class CertificateLinkProvider extends AbstractLinkProvider<Certificate>{
    private static final Class<CertificateController>
            CONTROLLER_CLASS = CertificateController.class;
    private final TagLinkProvider tagDtoLinkProvider;

    public CertificateLinkProvider(TagLinkProvider tagDtoLinkProvider) {
        this.tagDtoLinkProvider = tagDtoLinkProvider;
    }

    public void provideLinks(Certificate certificate) {
        long id = certificate.getId();
        provideIdLinks(CONTROLLER_CLASS, certificate, id, SELF_LINK, UPDATE_LINK, REPLACE_LINK, DELETE_LINK);
//todo добавить мб тегит сертификата в этот хатеос


    }
}
