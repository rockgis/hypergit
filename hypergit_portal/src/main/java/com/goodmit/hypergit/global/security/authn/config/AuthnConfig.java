package com.goodmit.hypergit.global.security.authn.config;

import com.goodmit.hypergit.global.security.authn.properties.SAMLProperties;
import com.goodmit.hypergit.global.security.authn.saml.sp.ContextProvider;
import com.goodmit.hypergit.global.security.authn.saml.sp.filter.EntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml2.binding.decoding.BaseSAML2MessageDecoder;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.binding.encoding.BaseSAML2MessageEncoder;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SAMLBootstrap;

@Slf4j
@Configuration
@EnableConfigurationProperties(value = {SAMLProperties.class})
public class AuthnConfig {

    @Bean
    public static SAMLBootstrap samlBootstrap() {
        return new SAMLBootstrap();
    }
    @Bean
    public EntryPoint samlEntryPoint(SAMLProperties samlProperties, @Value("${authentication.localAuth}") String localAuth) {
        return EntryPoint.builder()
                .samlProperties(samlProperties)
                .localAuth(localAuth)
                .encoder(saml2MessageEncoder())
                .build();
    }

    @Bean
    public BaseSAML2MessageEncoder saml2MessageEncoder() {
        return new HTTPRedirectDeflateEncoder();
    }

    @Bean
    public BaseSAML2MessageDecoder saml2MessageDecoder() {
        return new HTTPPostDecoder();
    }
    @Bean
    public ContextProvider samlContextProvider() {
        return new ContextProvider();
    }

}
