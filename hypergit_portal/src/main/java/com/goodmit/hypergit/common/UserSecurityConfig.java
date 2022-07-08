package com.goodmit.hypergit.common;

import com.goodmit.hypergit.global.security.authn.config.AuthnConfig;
import com.goodmit.hypergit.global.security.authn.properties.SAMLProperties;
import com.goodmit.hypergit.global.security.authn.saml.sp.ContextProvider;
import com.goodmit.hypergit.global.security.authn.saml.sp.SamlAuthProvider;
import com.goodmit.hypergit.global.security.authn.saml.sp.consumer.AssertionConsumer;
import com.goodmit.hypergit.global.security.authn.saml.sp.consumer.AssertionConsumerImpl;
import com.goodmit.hypergit.global.security.authn.saml.sp.filter.AssertionConsumerFilter;
import com.goodmit.hypergit.global.security.authn.saml.sp.filter.EntryPoint;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Import(value = {AuthnConfig.class})
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private SAMLProperties samlProperties;
    private EntryPoint samlEntryPoint;

    private ContextProvider samlContextProvider;
    protected UserSecurityConfig(SAMLProperties samlProperties, EntryPoint samlEntryPoint, ContextProvider samlContextProvider) {
        this.samlProperties = samlProperties;
        this.samlEntryPoint = samlEntryPoint;
        this.samlContextProvider = samlContextProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers(getResources()).permitAll()
                    .antMatchers(samlProperties.getAcs()+"/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic().authenticationEntryPoint(samlEntryPoint)
                .and()
                    .addFilterAfter(samlFilterChain(samlProperties), BasicAuthenticationFilter.class)
                .csrf().disable();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authBuilder) {
        authBuilder.authenticationProvider(authenticationProvider(assertionConsumer()));
    }

    @Bean
    public AssertionConsumer assertionConsumer() {
        return AssertionConsumerImpl.builder().assertionValidTime(30).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AssertionConsumer assertionConsumer) {
        return SamlAuthProvider.builder()
                .assertionConsumer(assertionConsumer)
                .build();
    }

    @Bean
    public FilterChainProxy samlFilterChain(SAMLProperties samlProperties) throws Exception {
        List<SecurityFilterChain> chains = new ArrayList<>();
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher(samlProperties.getAcs() + "/**"), samlFilter(samlProperties)));
        return new FilterChainProxy(chains);
    }

    @Bean
    public AssertionConsumerFilter samlFilter(SAMLProperties samlProperties) throws Exception {
        AssertionConsumerFilter samlFilter = AssertionConsumerFilter.builder()
                        .defaultFilterProcessesUrl(samlProperties.getAcs())
                        .samlContextProvider(samlContextProvider).build();

        samlFilter.setAuthenticationManager(authenticationManagerBean());
        samlFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        return samlFilter;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successRedirectHandler.setDefaultTargetUrl("/");
        return successRedirectHandler;
    }

    private String[] getResources() {
        return  Arrays.stream(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .toArray(String[]::new);
    }
}
