package com.goodmit.hypergit.global.security.config;

import com.goodmit.hypergit.global.security.authn.HAuthnProvider;
import com.goodmit.hypergit.global.security.authn.properties.ADProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
@EnableConfigurationProperties(value = {ADProperties.class})
public class AuthnConfig {

    @Bean
    public HAuthnProvider authnProvider(ADProperties adProperties) {
        return HAuthnProvider.builder()
                .authProvider(adAuthProvider(adProperties))
                .build();
    }


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

//    @Bean
//    @ConditionalOnBean(value = {AuthDBConfig.class})
////    @ConditionalOnProperty(prefix = "devdb")
//    public UserDetailsService dbUserDetailService(MemberRepository memberRepository) {
//        return DBUserDetailService.builder()
//                .memberRepository(memberRepository)
//                .build();
//    }
//
//
////    @Bean(name = "authProvider")
//    @ConditionalOnBean(value = {AuthDBConfig.class})
////    @ConditionalOnProperty(prefix = "devdb", name = "")
//    public AuthenticationProvider dbUserAuthProvider(MemberRepository memberRepository) {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(dbUserDetailService(memberRepository));
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
