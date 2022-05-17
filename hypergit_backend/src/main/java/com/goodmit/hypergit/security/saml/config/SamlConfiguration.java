package com.goodmit.hypergit.security.saml.config;

import com.goodmit.hypergit.security.key.KeyConfig;
import com.goodmit.hypergit.security.key.KeyService;
import com.goodmit.hypergit.security.saml.LocalSamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.SamlAuthHandler;
import com.goodmit.hypergit.security.saml.SamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.SamlResponseFilter;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.PaosBootstrap;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.impl.SingleSignOnServiceBuilder;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.saml.key.JKSKeyManager;

@Slf4j
@Configuration
@Import(value = {KeyConfig.class})
@EnableConfigurationProperties(value = {SamlProperties.class})
public class SamlConfiguration  {

    @Bean
    public static BeanFactoryPostProcessor samlInitializer() {
        return beanFactory -> {
            try {
                log.info("Initialize open saml...");
                PaosBootstrap.bootstrap();
            } catch (ConfigurationException e) {
                throw new FatalBeanException("Error invoking OpenSAML bootstrap", e);
            }
        };
    }

    @Bean
    public SamlResponseFilter samlResponseFilter(SamlProperties samlProperties,
                                                 SamlAuthHandler samlAuthHandler,
                                                 SamlPrincipalFactory samlPrincipalFactory) {
        return new SamlResponseFilter(samlProperties,samlAuthHandler,samlPrincipalFactory);
    }

    @Bean
    public SamlAuthHandler samlAuthHandler(SamlProperties samlProperties, KeyService keyService) throws XMLParserException {
        return new SamlAuthHandler(samlProperties,keyService);
    }

    @Bean
    public SamlPrincipalFactory samlPrincipalFactory(SamlProperties samlProperties) {
        return LocalSamlPrincipalFactory.builder().nameIdType(samlProperties.getNameIDType()).build();
    }
}
