package com.goodmit.hypergit.global.security.authn;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HAuthnProvider implements AuthenticationProvider {
    private final AuthenticationProvider adAuthProvider;


    @Builder
    private HAuthnProvider(@NonNull AuthenticationProvider adAuthProvider) {
        this.adAuthProvider = adAuthProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return adAuthProvider.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return adAuthProvider.supports(authentication);
    }
}
