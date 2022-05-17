package com.goodmit.hypergit.security.key;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@ConfigurationProperties(prefix = "security.key")
public class KeyProperties {
    private final String path;
    private final String passphrase;
    private final KeyStoreType type;
    private final String alias;
}
