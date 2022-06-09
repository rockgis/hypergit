package com.goodmit.hypergit.security.saml.auth;

import com.goodmit.hypergit.security.saml.auth.dao.SamlAttribute;
import lombok.Builder;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

public class LocalSamlPrincipalFactory extends SamlPrincipalFactory{

    private static final String KEY_USER_NAME="uid";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_FI="cn";

    @Builder
    protected LocalSamlPrincipalFactory(String nameIdType) {
        super(nameIdType);
    }

    @Override
    protected List<SamlAttribute> createAttributes(Authentication authentication) {
        return Arrays.asList(
                new SamlAttribute(KEY_USER_NAME,authentication.getName()),
                new SamlAttribute(KEY_USER_EMAIL,"test@test.com"),
                new SamlAttribute(KEY_USER_FI,authentication.getName())
        );
    }
}
