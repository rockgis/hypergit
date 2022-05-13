package com.goodmit.hypergit.security.saml.web;

import com.goodmit.hypergit.security.saml.config.SamlConfiguration;
import com.goodmit.hypergit.security.saml.config.SamlProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

        return ldapUserDetails.toString();
    }

    @PostMapping("/loginP")
    public void loginProcess(HttpServletRequest request) {
        log.info("req ====> {}",request.getRemoteHost());
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
