package com.goodmit.hypergit.identity.saml.application.controller;

import com.goodmit.hypergit.identity.saml.application.SAMLService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SamlController {

    private final SAMLService samlService;

    @GetMapping(value = "/metadata",produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<byte[]> getIDPMetadata() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("idp-metdata","idp-metadata.xml");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        byte[] contents = samlService.getIDPMetadata().getBytes(StandardCharsets.UTF_8);
        return new ResponseEntity(contents, headers, HttpStatus.OK);
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

}
