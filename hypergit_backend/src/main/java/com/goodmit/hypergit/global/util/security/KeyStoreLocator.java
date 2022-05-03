package com.goodmit.hypergit.global.util.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KeyStoreLocator {
    private final static String CERT_TYPE="X509";
    @Getter
    private static CertificateFactory certificateFactory;

    static {
        try{
            certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        } catch(CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyStore createKeyStore(Path keyFile, String keyPassword, KeyStoreType type) {
        try {
            KeyStore keyStore = KeyStore.getInstance(type.name());
            keyStore.load((keyFile == null)? null: Files.newInputStream(keyFile),keyPassword.toCharArray());
            return keyStore;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyStore createKeyStore(String keyPassword) {
        KeyStoreType type = KeyStoreType.valueOf(KeyStore.getDefaultType().toUpperCase());
        return createKeyStore(null,keyPassword,type);
    }

    public static KeyStore createKeyStore(String keyPassword,KeyStoreType type) {
        return createKeyStore(null,keyPassword,type);
    }

    public static void addPrivateKey(KeyStore keyStore, String alias, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException {
        char[] pwdArray = password.toCharArray();
        List<Certificate> certificateList = new ArrayList<>();
        certificateList.add(keyStore.getCertificate(alias));
        KeySpec spec = new PKCS8EncodedKeySpec(keyStore.getKey(alias,pwdArray).getEncoded());
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
        keyStore.setKeyEntry(alias,rsaPrivateKey,pwdArray,certificateList.toArray(new Certificate[certificateList.size()]));
    }

}
