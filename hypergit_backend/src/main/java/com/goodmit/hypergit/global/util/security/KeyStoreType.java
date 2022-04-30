package com.goodmit.hypergit.global.util.security;

import java.util.Optional;
import java.util.stream.Stream;

public enum KeyStoreType {
    JKS,
    PKCS12,
    JCEKS;
    public static KeyStoreType fromString(String keyType) {
        Optional<KeyStoreType> optional =Stream.of(values()).filter(stroeType->stroeType.toString().equalsIgnoreCase(keyType)).findFirst();
        if(!optional.isPresent()) {
            return JKS;
        }
        return optional.get();
    }
}
