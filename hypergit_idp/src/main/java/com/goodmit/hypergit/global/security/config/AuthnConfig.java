package com.goodmit.hypergit.global.security.config;

import com.goodmit.hypergit.global.config.AuthDBConfig;
import com.goodmit.hypergit.global.security.authn.HAuthnProvider;
import com.goodmit.hypergit.global.security.authn.db.DBUserDetailService;
import com.goodmit.hypergit.global.security.authn.properties.ADProperties;
import com.goodmit.hypergit.repository.MemberRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
@EnableConfigurationProperties(value = {ADProperties.class})
public class AuthnConfig {

    @Bean
    public HAuthnProvider authnProvider() {
        return HAuthnProvider.builder()
                .authProvider(null)
                .build();
    }

    @Bean(name = "authProvider")
    @ConditionalOnBean(value = {ADProperties.class})
    public AuthenticationProvider adAuthProvider(ADProperties adProperties) {
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

    @Bean
    @ConditionalOnProperty(prefix = "authdb")
    public UserDetailsService dbUserDetailService(MemberRepository memberRepository) {
        return DBUserDetailService.builder()
                .memberRepository(memberRepository)
                .build();
    }


//    @Bean(name = "authProvider")
    @ConditionalOnBean(value = {ADProperties.class})
    @ConditionalOnProperty(prefix = "authdb", name = "")
    public AuthenticationProvider dbUserAuthProvider(MemberRepository memberRepository) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(dbUserDetailService(memberRepository));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
