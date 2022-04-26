package com.goodmit.hypergit.global.util.security;

import lombok.Getter;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class KeyStoreLocator {
    private final static String CERT_TYPE="X.509";
    @Getter
    private static CertificateFactory certificateFactory;

    static {
        try{
            certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        } catch(CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyStore createKeyStore(String pemPassPhrase) {
        return createKeyStore(pemPassPhrase,KeyStoreType.valueOf(KeyStore.getDefaultType().toUpperCase()));
    }

    public static KeyStore createKeyStore(String pemPassPhrase,KeyStoreType type) {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(type.name());
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } finally {
            return keyStore;
        }
    }

}
