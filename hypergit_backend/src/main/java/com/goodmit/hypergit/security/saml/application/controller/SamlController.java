package com.goodmit.hypergit.security.saml.application.controller;

import com.goodmit.hypergit.security.saml.application.SAMLService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SamlController {

    private final SAMLService samlService;

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
