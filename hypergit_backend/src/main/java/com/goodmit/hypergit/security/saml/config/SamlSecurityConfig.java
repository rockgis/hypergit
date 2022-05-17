package com.goodmit.hypergit.security.saml.config;

import com.goodmit.hypergit.security.saml.SamlResponseFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.SessionCookieConfig;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EnableWebSecurity
@Order(1)
public class SamlSecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private SamlResponseFilter samlResponseFilter;
    @NonNull private SamlProperties samlProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(request->{
                    request.antMatchers(samlProperties.getAuthUrl()).authenticated();
                    request.anyRequest().authenticated();
                })
                .addFilterAfter(samlResponseFilter, FilterSecurityInterceptor.class)
//                .httpBasic()
                .formLogin()
                .and()
                .logout();

    }

}
