package com.goodmit.hypergit.security.saml.key;

import lombok.Builder;
import lombok.Getter;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.springframework.security.saml.key.JKSKeyManager;

import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class KeyService {
    private final KeyProperties keyProperties;
    @Getter
    private final JKSKeyManager keyManager;

    @Builder
    protected KeyService(KeyProperties keyProperties, JKSKeyManager keyManager) {
        this.keyProperties = keyProperties;
        this.keyManager = keyManager;
    }

    public Credential resolveCredential() throws SecurityException {
        return keyManager.resolveSingle(new CriteriaSet(new EntityIDCriteria(keyProperties.getAlias())));
    }

    public String getPassphrase() {
        return keyProperties.getPassphrase();
    }

    public Key getKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return keyManager.getKeyStore().getKey(keyProperties.getAlias(),keyProperties.getPassphrase().toCharArray());
    }

}
