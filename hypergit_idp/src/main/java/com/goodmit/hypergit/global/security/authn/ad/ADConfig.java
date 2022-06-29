package com.goodmit.hypergit.global.security.authn.ad;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Slf4j
@EnableWebSecurity
@EnableConfigurationProperties(value = {ADProperties.class})
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ADConfig {

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider adAuthProvider(ADProperties adProperties) {
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

