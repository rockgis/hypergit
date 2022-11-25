package com.uiscloud.hypergit.identity.saml.auth;

import com.uiscloud.hypergit.identity.saml.auth.dao.SamlAttribute;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LocalSamlPrincipalFactory extends SamlPrincipalFactory{

    private static final String KEY_USER_NAME="uid";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_FI="urn:oid:2.5.4.11";
//    private static final String KEY_USER_FI="cn";

    @Builder
    protected LocalSamlPrincipalFactory(String nameIdType) {
        super(nameIdType);
    }

    @Override
    protected List<SamlAttribute> createAttributes(Authentication authentication) {
        List<String> authz = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        List<SamlAttribute> attributes = Arrays.asList(
                new SamlAttribute(KEY_USER_NAME,authentication.getName()),
                new SamlAttribute(KEY_USER_EMAIL,"test@test.com"),
                new SamlAttribute(KEY_USER_FI, Arrays.asList("admin","test"))
        );

        return attributes;
    }
}
