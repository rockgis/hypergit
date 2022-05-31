package com.goodmit.hypergit.security.saml.web;

import com.goodmit.hypergit.security.saml.application.SAMLService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@CrossOrigin
public class SamlController {

    private final SAMLService samlService;

    @Builder
    protected SamlController(SAMLService samlService) {
        this.samlService = samlService;
    }

    @GetMapping(value = "/metadata",produces = MediaType.APPLICATION_XML_VALUE)
    public String getIDPMetadata() {
        String tt = samlService.getIDPMetadata();
        log.info(tt);
        return tt;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

}
