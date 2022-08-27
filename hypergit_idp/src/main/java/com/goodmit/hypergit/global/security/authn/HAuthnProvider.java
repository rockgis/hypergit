package com.goodmit.hypergit.global.security.authn;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class HAuthnProvider implements AuthenticationProvider {
    private final AuthenticationProvider authProvider;


    @Builder
    private HAuthnProvider(@NonNull AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authen in");
        return authProvider.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authProvider.supports(authentication);
    }
}
