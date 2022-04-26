package com.goodmit.hypergit.security.config;

import com.goodmit.hypergit.security.saml.SamlAuthHandler;
import com.goodmit.hypergit.security.saml.SamlProperties;
import com.goodmit.hypergit.security.saml.SamlResponseFilter;
import org.opensaml.xml.parse.XMLParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.saml.key.JKSKeyManager;

import javax.servlet.SessionCookieConfig;

@EnableWebSecurity
@EnableConfigurationProperties(value = {SamlProperties.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .deleteCookies("JSESSIONID","idp.session");
//                .loginPage("/login").permitAll()
//                .failureUrl("/login?error=true").permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .and()
//                .addFilterAfter(samlResponseFilter(), FilterSecurityInterceptor.class)
//                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico","/*.js");
    }

    /*
     * TODO: change userDetailService with openLdap
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails test = User
                .withUsername("test")
                .password(passwordEncoder.encode("test"))
                .roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(test,admin);

    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setName("idp.session");
            sessionCookieConfig.setHttpOnly(true);
        };
    }

    @Bean
    public JKSKeyManager keyManager(SamlProperties samlProperties) {
        return null;
    }

    @Bean
    public SamlResponseFilter samlResponseFilter(SamlProperties samlProperties) {
        return new SamlResponseFilter(samlProperties.getAuthUrl());
    }

    @Bean
    public SamlAuthHandler samlAuthHandler(SamlProperties samlProperties) throws XMLParserException {
        return new SamlAuthHandler(samlProperties);
    }

}
