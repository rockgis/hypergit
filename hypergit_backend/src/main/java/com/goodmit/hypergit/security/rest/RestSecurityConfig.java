package com.goodmit.hypergit.security.rest;

import com.goodmit.hypergit.security.key.KeyConfig;
import com.goodmit.hypergit.security.key.KeyService;
import com.goodmit.hypergit.security.rest.web.AuthRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Import(value = {KeyConfig.class})
@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/api/**").authorizeRequests()
                .antMatchers("/api/auth").permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public TokenProvider tokenProvider(KeyService keyService) {
        return new TokenProvider(keyService, userDetailsService());
    }

    @Bean
    public TokenFilter tokenFilter(TokenProvider tokenProvider) {
        return new TokenFilter(tokenProvider);
    }

    @Bean
    public AuthRestController authRestController(TokenProvider tokenProvider) throws Exception {
        return AuthRestController.builder()
                .authenticationManager(authenticationManager())
                .tokenProvider(tokenProvider)
                .build();
    }
}
