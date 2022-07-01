package com.goodmit.hypergit.global.security.config;

import com.goodmit.hypergit.global.security.authn.HAuthnProvider;
import com.goodmit.hypergit.global.security.authn.db.DBUserDetailService;
import com.goodmit.hypergit.global.security.authn.properties.ADProperties;
import com.goodmit.hypergit.repository.MemberRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
@EnableConfigurationProperties(value = {ADProperties.class})
public class AuthnConfig {

    private ADProperties adProperties;
    private MemberRepository memberRepository;

    protected AuthnConfig(ADProperties adProperties , MemberRepository memberRepository) {
        this.adProperties = adProperties;
        this.memberRepository = memberRepository;
    }

    @Bean
    public HAuthnProvider authnProvider() {
        return HAuthnProvider.builder()
                .authProvider(dbUserAuthProvider())
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

    @Bean
    public UserDetailsService dbUserDetailService() {
        return DBUserDetailService.builder()
                .memberRepository(memberRepository)
                .build();
    }


    @Bean
    public AuthenticationProvider dbUserAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(dbUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
