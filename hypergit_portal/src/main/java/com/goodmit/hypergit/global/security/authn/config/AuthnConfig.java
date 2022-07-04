package com.goodmit.hypergit.global.security.authn.config;

import com.goodmit.hypergit.global.security.authn.properties.SAMLProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationProvider;

@Slf4j
@Configuration
@EnableConfigurationProperties(value = {SAMLProperties.class})
@EnableJpaRepositories(
        basePackages = {"com.goodmit.hypergit"}
)
public class AuthnConfig {

    @Bean
    public AuthenticationProvider samlAuthnProvider() {
        return null;
    }
}
