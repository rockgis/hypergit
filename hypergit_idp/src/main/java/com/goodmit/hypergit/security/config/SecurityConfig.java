package com.goodmit.hypergit.security.config;

import com.goodmit.hypergit.security.authn.HAuthnProvider;
import com.goodmit.hypergit.security.authn.ad.ADConfig;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Slf4j
@EnableWebSecurity
@Import(value = {ADConfig.class})
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private ActiveDirectoryLdapAuthenticationProvider adAuthProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
     /*
        auth.ldapAuthentication()
                .userDnPatterns("CN={0},OU=Users, OU=PlatformDivision,DC=GOODMIT,DC=COM")
                .userSearchBase("OU=Users,OU=PlatformDivision,DC=GOODMIT,DC=COM")
                .groupSearchBase("OU=Groups,OU=PlatformDivision,DC=GOODMIT,DC=COM")
                .contextSource()
                .url("ldap://10.200.101.19:389/OU=PlatformDivision,DC=GOODMIT,DC=COM")
                .managerDn("CN=samltest,OU=Users,OU=PlatformDivision,DC=GOODMIT,DC=COM")
                .managerPassword("git08021!")
                .and()
                .passwordCompare();
      */

        auth.authenticationProvider(authnProvider());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico","/*.js");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public HAuthnProvider authnProvider() {
        return HAuthnProvider.builder()
                .adAuthProvider(adAuthProvider)
                .build();
    }
}
