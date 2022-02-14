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
import java.util.stream.Collectors;

import static com.epam.esm.util.RequestParammetr.DEFAULT_PAGE;
import static com.epam.esm.util.RequestParammetr.DEFAULT_SIZE;
import static com.epam.esm.util.RequestParammetr.PAGE;
import static com.epam.esm.util.RequestParammetr.PART_NAME;
import static com.epam.esm.util.RequestParammetr.SIZE;
import static com.epam.esm.util.RequestParammetr.TAG_NAME;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

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
    public void create(@RequestBody Certificate certificate) {
        certificateService.create(certificate);
        certificateLinkProvider.provideLinks(certificate);
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
        return certificates.stream()
                .peek(certificateLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Certificate updateById(@PathVariable("id") int id,
                                  @RequestBody Certificate certificate) {
        requestParametersValidator.idParamValid(id);
        return certificateService.updateById(id, certificate);
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
