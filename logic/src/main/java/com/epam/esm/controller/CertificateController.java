package com.epam.esm.controller;


import com.epam.esm.link.CertificateLinkProvider;
import com.epam.esm.model.Certificate;
import com.epam.esm.service.api.CertificateService;
import com.epam.esm.validator.impl.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/certificates")
public class CertificateController {

    //todo const to interface
    public static final String TAG_NAME = "tag_name";
    public static final String PART_NAME = "part_name";
    public static final String SIZE = "size";
    public static final String PAGE = "page";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";


    private final CertificateService certificateService;
    private final CertificateLinkProvider certificateLinkProvider;
    private final RequestParametersValidator requestParametersValidator;

    @Autowired
    public CertificateController(CertificateService certificateService,
                                 CertificateLinkProvider giftCertificateLinkProvider,
                                 RequestParametersValidator requestParametersValidator) {
        this.certificateService = certificateService;
        this.certificateLinkProvider = giftCertificateLinkProvider;
        this.requestParametersValidator = requestParametersValidator;
    }

    @PostMapping

    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Certificate certificate,
                       HttpServletResponse response) {
        certificateService.create(certificate);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Certificate> findAll(
            @RequestParam(name = TAG_NAME, required = false) List<String> tagNames,
            @RequestParam(name = PART_NAME, required = false) String partName,
            @RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size
    ) {


        requestParametersValidator.paginationParamValid(page, size);
        List<Certificate> certificates = certificateService.findAll(tagNames, partName, page, size);
        certificates.forEach(certificateLinkProvider::provideLinks);

        return certificates;
    }


    //todo
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Certificate updateById(@PathVariable("id") int id,
                                  @RequestBody Certificate сertificate) {


        requestParametersValidator.idParamValid(id);
        return certificateService.updateById(id, сertificate);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Certificate findById(@PathVariable("id") int id) {

        requestParametersValidator.idParamValid(id);
        Certificate certificate = certificateService.findById(id);

        certificateLinkProvider.provideLinks(certificate);
        return certificate;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteById(@PathVariable("id") int id) {
        requestParametersValidator.idParamValid(id);
        certificateService.deleteById(id);
    }


}
