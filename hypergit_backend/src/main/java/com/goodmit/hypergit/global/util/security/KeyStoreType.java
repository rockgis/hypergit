package com.goodmit.hypergit.global.util.security;

public enum KeyStoreType {
    JKS,
    PKCS12,
    JCEKS;
    public KeyStoreType findType(String keyType) {
        return KeyStoreType.valueOf(keyType);
    }
}
