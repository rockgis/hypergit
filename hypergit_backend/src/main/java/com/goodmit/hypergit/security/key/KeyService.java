package com.goodmit.hypergit.security.key;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.springframework.security.saml.key.JKSKeyManager;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyService {
    private final KeyProperties keyProperties;
    private final JKSKeyManager keyManager;

    public Credential resolveCredential() throws SecurityException {
        return keyManager.resolveSingle(new CriteriaSet(new EntityIDCriteria(keyProperties.getAlias())));
    }

    public String getPassphrase() {
        return keyProperties.getPassphrase();
    }
}
