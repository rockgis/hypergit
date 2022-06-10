package com.goodmit.hypergit.security.saml.config;

import com.goodmit.hypergit.security.saml.key.KeyProperties;
import com.goodmit.hypergit.security.saml.key.KeyService;
import com.goodmit.hypergit.security.saml.auth.LocalSamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.auth.SamlAuthHandler;
import com.goodmit.hypergit.security.saml.auth.SamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.auth.SamlResponseFilter;
import com.goodmit.hypergit.security.saml.application.SAMLService;
import com.goodmit.hypergit.security.saml.application.impl.SAMLServiceImpl;
import com.goodmit.hypergit.security.saml.key.KeyStoreLocator;
import com.goodmit.hypergit.security.saml.metadata.IdpMetadataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.metadata.MetadataGenerator;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;

@Slf4j
@Configuration
@EnableConfigurationProperties(value = {SamlProperties.class})
public class SamlConfiguration  {

    @Bean
    public static SAMLBootstrap samlBootstrap() {
        return new SAMLBootstrap();
    }

    @Bean
    public SamlResponseFilter samlResponseFilter(SamlProperties samlProperties,
                                                 SamlAuthHandler samlAuthHandler,
                                                 SamlPrincipalFactory samlPrincipalFactory) {
        return SamlResponseFilter.builder()
                .samlProperties(samlProperties)
                .samlAuthHandler(samlAuthHandler)
                .samlPrincipalFactory(samlPrincipalFactory)
                .build();
    }

    @Bean
    public SamlAuthHandler samlAuthHandler(SamlProperties samlProperties, KeyService keyService) throws XMLParserException {
        return SamlAuthHandler.builder()
                .samlProperties(samlProperties)
                .keyService(keyService)
                .build();
    }

    @Bean
    public SamlPrincipalFactory samlPrincipalFactory(SamlProperties samlProperties) {
        return LocalSamlPrincipalFactory.builder()
                .nameIdType(samlProperties.getNameIDType())
                .build();
    }

    @Bean
    public MetadataGenerator metadataGenerator(SamlProperties samlProperties, @Value("${server.port}") String port) {
        MetadataGenerator metadataGenerator = IdpMetadataGenerator.builder()
                .sloURL(samlProperties.getAuthUrl())
                .ssoURL(samlProperties.getAuthUrl())
                .build();
        metadataGenerator.setEntityBaseURL("http://"+ samlProperties.getBindingAddress()+":"+port);
        return metadataGenerator;
    }

//    @Bean
//    public SamlController samlController(SAMLService samlService) {
//        return SamlController.builder()
//                .samlService(samlService)
//                .build();
//    }

    @Bean
    public SAMLService samlService(SamlProperties samlProperties, MetadataGenerator metadataGenerator, KeyService keyService) {
        return SAMLServiceImpl.builder()
                .metadataGenerator(metadataGenerator)
                .samlProperties(samlProperties)
                .keyService(keyService)
                .build();
    }


    @Bean
    public StaticBasicParserPool parserPool() throws XMLParserException {
        StaticBasicParserPool parserPool = new StaticBasicParserPool();
        parserPool.initialize();
        return parserPool;
    }

    @Bean
    public JKSKeyManager keyManager(SamlProperties samlProperties)
            throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException {
        String keyAlias = samlProperties.getKey().getAlias();
        String keyPassword = samlProperties.getKey().getPassphrase();
        KeyStore keyStore = KeyStoreLocator.createKeyStore(
//                ResourceUtils.getFile(keyProperties.getPath()).toPath(),
                samlProperties.getKey().getPath(),
                keyPassword,
                samlProperties.getKey().getType());
        KeyStoreLocator.addPrivateKey(keyStore,keyAlias,keyPassword);
        return new JKSKeyManager(keyStore, Collections.singletonMap(keyAlias,keyPassword),keyAlias);
    }

    @Bean
    public KeyService keyService(SamlProperties samlProperties, JKSKeyManager keyManager) {
        return KeyService.builder()
                .keyProperties(samlProperties.getKey())
                .keyManager(keyManager)
                .build();
    }

}
