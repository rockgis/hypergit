package com.goodmit.hypergit.global.security.authn.saml.sp;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PreAuthToken extends AbstractAuthenticationToken {
    @Getter
    private SamlContext context;

    @Builder
    protected PreAuthToken(SamlContext context) {
        super(null);
        this.context = context;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
