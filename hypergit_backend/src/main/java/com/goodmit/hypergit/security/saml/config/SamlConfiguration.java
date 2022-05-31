package com.goodmit.hypergit.security.saml.config;

import com.goodmit.hypergit.security.key.KeyConfig;
import com.goodmit.hypergit.security.key.KeyService;
import com.goodmit.hypergit.security.saml.LocalSamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.SamlAuthHandler;
import com.goodmit.hypergit.security.saml.SamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.SamlResponseFilter;
import com.goodmit.hypergit.security.saml.application.SAMLService;
import com.goodmit.hypergit.security.saml.application.impl.SAMLServiceImpl;
import com.goodmit.hypergit.security.saml.metadata.IdpMetadataGenerator;
import com.goodmit.hypergit.security.saml.web.SamlController;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.metadata.MetadataGenerator;

import java.net.UnknownHostException;

@Slf4j
@Configuration
@Import(value = {KeyConfig.class})
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
    public MetadataGenerator metadataGenerator(SamlProperties samlProperties, JKSKeyManager keyManager, @Value("${server.port}") String port) throws UnknownHostException {
        MetadataGenerator metadataGenerator = IdpMetadataGenerator.builder()
                .sloURL(samlProperties.getAuthUrl())
                .ssoURL(samlProperties.getAuthUrl())
                .build();
        metadataGenerator.setEntityBaseURL("http://"+ samlProperties.getBindingAddress()+":"+port);
        return metadataGenerator;
    }

    @Bean
    public SamlController samlController(SAMLService samlService) {
        return SamlController.builder()
                .samlService(samlService)
                .build();
    }

    @Bean
    public SAMLService samlService(SamlProperties samlProperties, MetadataGenerator metadataGenerator, JKSKeyManager keyManager) {
        return SAMLServiceImpl.builder()
                .metadataGenerator(metadataGenerator)
                .samlProperties(samlProperties)
                .keyManager(keyManager)
                .build();
    }


    @Bean
    public StaticBasicParserPool parserPool() throws XMLParserException {
        StaticBasicParserPool parserPool = new StaticBasicParserPool();
        parserPool.initialize();
        return parserPool;
    }

}
