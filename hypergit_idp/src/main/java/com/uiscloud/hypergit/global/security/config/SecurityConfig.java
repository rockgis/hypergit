package com.goodmit.hypergit.global.security.config;

import com.goodmit.hypergit.global.security.authn.HAuthnProvider;
import com.goodmit.hypergit.identity.saml.auth.filter.SamlResponseFilter;
import com.goodmit.hypergit.identity.saml.config.SamlConfiguration;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Slf4j
@EnableWebSecurity
@Import(value = {SamlConfiguration.class, AuthnConfig.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SamlResponseFilter samlResponseFilter;
    private AuthenticationProvider authnProvider;

    protected SecurityConfig(@NonNull HAuthnProvider authnProvider,
                             @NonNull SamlResponseFilter samlResponseFilter) {
        this.authnProvider = authnProvider;
        this.samlResponseFilter = samlResponseFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable()
                    .authorizeRequests()
                    .antMatchers("/error").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterAfter(samlResponseFilter, FilterSecurityInterceptor.class)
                    .formLogin().permitAll()
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies()
                    .clearAuthentication(true)
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");
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
        //auth.authenticationProvider(authnProvider);


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


}
