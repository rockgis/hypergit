package com.goodmit.hypergit.security.saml;

import com.goodmit.hypergit.global.util.security.KeyStoreType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@ConstructorBinding
@ConfigurationProperties(prefix = "saml.idp")
public class SamlProperties {

    private final String authUrl;
    private final String entityId;
    private final int expired;
    private final int clockSkew;

    private final String keyUrl;
    private final String keyPassphrase;
    private final String keyAlias;
    private final KeyStoreType keyType;

    private final String nameIDType;

}
