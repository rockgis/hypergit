package com.uiscloud.hypergit.identity.saml.config;

import com.uiscloud.hypergit.identity.saml.application.SAMLService;
import com.uiscloud.hypergit.identity.saml.application.impl.SAMLServiceImpl;
import com.uiscloud.hypergit.identity.saml.auth.LocalSamlPrincipalFactory;
import com.uiscloud.hypergit.identity.saml.auth.SamlPrincipalFactory;
import com.uiscloud.hypergit.identity.saml.auth.filter.SamlResponseFilter;
import com.uiscloud.hypergit.identity.saml.application.KeyService;
import com.uiscloud.hypergit.identity.saml.config.properties.SamlProperties;
import com.uiscloud.hypergit.identity.saml.key.KeyStoreLocator;
import com.uiscloud.hypergit.identity.saml.metadata.BindingType;
import com.uiscloud.hypergit.identity.saml.auth.SamlAuthHandler;
import com.uiscloud.hypergit.identity.saml.metadata.IdpMetadataGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.processor.SAMLProcessor;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.util.StringUtils;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public MetadataGenerator metadataGenerator(SamlProperties samlProperties, @Value("${server.port}") String port, KeyManager keyManager) {

        MetadataGenerator metadataGenerator = IdpMetadataGenerator.builder()
                .sloURL(samlProperties.getAuthUrl())
                .ssoURL(samlProperties.getAuthUrl())
                .build();

        String baseUrl = StringUtils.hasText(samlProperties.getBindingAddress()) && samlProperties.getBindingAddress().startsWith("http")?
                samlProperties.getBindingAddress():"http://" + samlProperties.getBindingAddress() + ":" + port;

        metadataGenerator.setEntityBaseURL(baseUrl);
        metadataGenerator.setEntityId(samlProperties.getEntityId());
        metadataGenerator.setKeyManager(keyManager);
        Collection<String> bindingsSSO = samlProperties.getSsoBindings().stream().map(BindingType::getBindingUri).collect(Collectors.toList());
        Collection<String> bindingsSLO = samlProperties.getSloBindings().stream().map(BindingType::getBindingUri).collect(Collectors.toList());
        metadataGenerator.setBindingsSSO(bindingsSSO);
        metadataGenerator.setBindingsSLO(bindingsSLO);
        metadataGenerator.setNameID(Arrays.asList(samlProperties.getNameIDType()));
        return metadataGenerator;
    }


    @Bean
    public SAMLService samlService(SamlProperties samlProperties, MetadataGenerator metadataGenerator, KeyService keyService) {
        return SAMLServiceImpl.builder()
                .metadataGenerator(metadataGenerator)
                .samlProperties(samlProperties)
                .keyService(keyService)
                .build();
    }


    @SneakyThrows
    @Bean
    public StaticBasicParserPool parserPool()  {
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

    @Bean
    public SAMLProcessor samlSSOProcessor(SamlProperties samlProperties) {
        List list = samlProperties.getSsoBindings()
                .stream().map(bindingType -> bindingType.createSAMLBinding(parserPool()))
                .collect(Collectors.toList());
        return new SAMLProcessorImpl(list);
    }

}
