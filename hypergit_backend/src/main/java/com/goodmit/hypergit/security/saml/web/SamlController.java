package com.goodmit.hypergit.security.saml.web;

import com.goodmit.hypergit.security.saml.SamlConfiguration;
import com.goodmit.hypergit.security.saml.SamlProperties;
import com.sun.security.auth.LdapPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Import({SamlConfiguration.class})
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SamlController {

    private final SamlProperties samlProperties;

    @GetMapping("/")
    public String metaData() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LdapUserDetailsImpl ldapUserDetails = (LdapUserDetailsImpl) authentication.getPrincipal();
        log.info("{}",ldapUserDetails);

        return "metadata";
    }
}
