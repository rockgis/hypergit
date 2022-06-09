package com.goodmit.hypergit.security.key;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.key.JKSKeyManager;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;

@Configuration
@EnableConfigurationProperties(value = {KeyProperties.class})
public class KeyConfig {

    @Bean
    public JKSKeyManager keyManager(KeyProperties keyProperties)
            throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException {
        String keyAlias = keyProperties.getAlias();
        String keyPassword = keyProperties.getPassphrase();
        KeyStore keyStore = KeyStoreLocator.createKeyStore(
//                ResourceUtils.getFile(keyProperties.getPath()).toPath(),
                keyProperties.getPath(),
                keyPassword,
                keyProperties.getType());
        KeyStoreLocator.addPrivateKey(keyStore,keyAlias,keyPassword);
        return new JKSKeyManager(keyStore, Collections.singletonMap(keyAlias,keyPassword),keyAlias);
    }

    @Bean
    public KeyService keyService(KeyProperties keyProperties, JKSKeyManager keyManager) {
        return new KeyService(keyProperties,keyManager);
    }
}
