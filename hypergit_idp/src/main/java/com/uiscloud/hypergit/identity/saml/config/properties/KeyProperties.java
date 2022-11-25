package com.uiscloud.hypergit.identity.saml.config.properties;

import com.uiscloud.hypergit.identity.saml.key.KeyStoreType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyProperties {
    private final String path;
    private final String passphrase;
    private final KeyStoreType type;
    private final String alias;
}
