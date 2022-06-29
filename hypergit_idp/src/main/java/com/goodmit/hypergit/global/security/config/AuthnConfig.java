package com.goodmit.hypergit.global.security.config;

import com.goodmit.hypergit.global.security.authn.HAuthnProvider;
import com.goodmit.hypergit.global.security.authn.properties.ADProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
@EnableConfigurationProperties(value = {ADProperties.class})
public class AuthnConfig {

    private ADProperties adProperties;

    protected AuthnConfig(ADProperties adProperties) {
        this.adProperties = adProperties;
    }

    @Bean
    public HAuthnProvider authnProvider() {
        return HAuthnProvider.builder()
                .adAuthProvider(adAuthProvider())
                .build();
    }

    @Bean
    public AuthenticationProvider adAuthProvider() {
        ActiveDirectoryLdapAuthenticationProvider authenticationProvider =
                new ActiveDirectoryLdapAuthenticationProvider(
                        adProperties.getDomain(),
                        adProperties.getUrl(),
                        adProperties.getRootDn()
                );
        authenticationProvider.setConvertSubErrorCodesToExceptions(true);
        authenticationProvider.setUseAuthenticationRequestCredentials(true);
        return authenticationProvider;
    }
}
