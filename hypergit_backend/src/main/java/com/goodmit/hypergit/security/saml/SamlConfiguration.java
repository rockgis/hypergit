package com.goodmit.hypergit.security.saml;

import com.goodmit.hypergit.global.util.security.KeyStoreLocator;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.key.JKSKeyManager;

import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;

@Slf4j
@Configuration
public class SamlConfiguration {

    @Bean
    public static BeanFactoryPostProcessor samlInitializer() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                try {
                    log.info("Initialize open saml...");
                    DefaultBootstrap.bootstrap();
                } catch (ConfigurationException e) {
                    throw new FatalBeanException("Error invoking OpenSAML bootstrap", e);
                }
            }
        };
    }

    @Bean
    public JKSKeyManager keyManager(SamlProperties samlProperties)
            throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException {
        String keyAlias = samlProperties.getKeyAlias();
        String keyPassword = samlProperties.getKeyPassphrase();
        KeyStore keyStore = KeyStoreLocator.createKeyStore(Paths.get(samlProperties.getKeyUrl()),keyPassword, samlProperties.getKeyType());
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
    public SamlAuthHandler samlAuthHandler(SamlProperties samlProperties,JKSKeyManager keyManager) throws XMLParserException {
        return new SamlAuthHandler(samlProperties,keyManager);
    }


    @Bean
    public SamlPrincipalFactory samlPrincipalFactory(SamlProperties samlProperties) {
        return LocalSamlPricipalFactory.builder().nameIdType(samlProperties.getNameIDType()).build();
    }
}
