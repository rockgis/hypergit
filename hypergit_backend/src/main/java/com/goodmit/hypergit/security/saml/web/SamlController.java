package com.goodmit.hypergit.security.saml.web;

import com.goodmit.hypergit.security.saml.application.SAMLService;
import com.goodmit.hypergit.security.saml.config.SamlConfiguration;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
