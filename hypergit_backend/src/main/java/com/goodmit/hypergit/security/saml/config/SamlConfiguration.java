package com.goodmit.hypergit.security.saml.config;

import com.goodmit.hypergit.global.util.security.KeyStoreLocator;
import com.goodmit.hypergit.security.saml.LocalSamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.SamlAuthHandler;
import com.goodmit.hypergit.security.saml.SamlPrincipalFactory;
import com.goodmit.hypergit.security.saml.SamlResponseFilter;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.DefaultBootstrap;
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
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.websso.SingleLogoutProfileImpl;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;

@Slf4j
@Configuration
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
    public JKSKeyManager keyManager(SamlProperties samlProperties)
            throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException, FileNotFoundException {
        String keyAlias = samlProperties.getKeyAlias();
        String keyPassword = samlProperties.getKeyPassphrase();
        KeyStore keyStore = KeyStoreLocator.createKeyStore(
                ResourceUtils.getFile(samlProperties.getKeyUrl()).toPath(),
                keyPassword,
                samlProperties.getKeyType());
        KeyStoreLocator.addPrivateKey(keyStore,keyAlias,keyPassword);
        return new JKSKeyManager(keyStore, Collections.singletonMap(keyAlias,keyPassword),keyAlias);
    }


    @Bean
    public SamlResponseFilter samlResponseFilter(SamlProperties samlProperties,
                                                 SamlAuthHandler samlAuthHandler,
                                                 SamlPrincipalFactory samlPrincipalFactory) {
        return new SamlResponseFilter(samlProperties,samlAuthHandler,samlPrincipalFactory);
    }

    @Bean
    public SamlAuthHandler samlAuthHandler(SamlProperties samlProperties, JKSKeyManager keyManager, SingleSignOnService singleSignOnService) throws XMLParserException {
        return new SamlAuthHandler(samlProperties,keyManager);
    }

    @Bean
    public SamlPrincipalFactory samlPrincipalFactory(SamlProperties samlProperties) {
        return LocalSamlPrincipalFactory.builder().nameIdType(samlProperties.getNameIDType()).build();
    }

    @Bean
    public SingleSignOnService singleSignOnService() {
        return new SingleSignOnServiceBuilder().buildObject();
    }

}
