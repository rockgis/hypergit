package com.goodmit.hypergit.security.saml.config;

import com.goodmit.hypergit.security.saml.SamlResponseFilter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EnableWebSecurity
@Order(2)
public class SamlSecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private SamlResponseFilter samlResponseFilter;
    @NonNull private SamlProperties samlProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable().authorizeRequests()
//                .antMatchers("/").permitAll()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(samlResponseFilter, FilterSecurityInterceptor.class)
                .formLogin().permitAll()
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies()
                .clearAuthentication(true)
                .permitAll();

    }

}
