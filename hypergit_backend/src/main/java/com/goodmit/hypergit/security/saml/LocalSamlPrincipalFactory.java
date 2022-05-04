package com.goodmit.hypergit.security.saml;

import com.goodmit.hypergit.security.saml.dao.SamlAttribute;
import lombok.Builder;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

public class LocalSamlPrincipalFactory extends SamlPrincipalFactory{

    private static final String KEY_USER_NAME="User.Username";
    private static final String KEY_USER_EMAIL="User.Email";
    private static final String KEY_USER_FI="User.FederationIdentifier";

    @Builder
    protected LocalSamlPrincipalFactory(String nameIdType) {
        super(nameIdType);
    }

    @Override
    protected List<SamlAttribute> createAttributes(Authentication authentication) {
        return Arrays.asList(
                new SamlAttribute(KEY_USER_NAME,authentication.getName()),
                new SamlAttribute(KEY_USER_EMAIL,"test@test.com"),
                new SamlAttribute(KEY_USER_FI,"test@test.com")
        );
    }
}
