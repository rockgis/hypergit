package com.goodmit.hypergit.security.config;

import com.goodmit.hypergit.global.util.security.KeyStoreLocator;
import com.goodmit.hypergit.security.saml.*;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.XMLParserException;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
import org.springframework.security.saml.key.KeyManager;

import javax.servlet.SessionCookieConfig;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;

@Slf4j
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
    public JKSKeyManager keyManager(SamlProperties samlProperties)
            throws  CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException {
        String keyAlias = samlProperties.getKeyAlias();
        String keyPassword = samlProperties.getKeyPassphrase();
        KeyStore keyStore = KeyStoreLocator.createKeyStore(Paths.get(samlProperties.getKeyUrl()),keyPassword, samlProperties.getKeyType());
        KeyStoreLocator.addPrivateKey(keyStore,keyAlias,keyPassword);
        return new JKSKeyManager(keyStore, Collections.singletonMap(keyAlias,keyPassword),keyAlias);
    }


    @Bean
    public SamlResponseFilter samlResponseFilter(SamlProperties samlProperties,
                                                 SamlAuthHandler samlAuthHandler,
                                                 SamlPrincipalFactory samlPrincipalFactory) {
        return new SamlResponseFilter(samlProperties,samlAuthHandler,samlPrincipalFactory);
    }

    @Bean
    public SamlAuthHandler samlAuthHandler(SamlProperties samlProperties,JKSKeyManager keyManager) throws XMLParserException {
        return new SamlAuthHandler(samlProperties,keyManager);
    }

    @Bean
    public static BeanFactoryPostProcessor samlInitializer() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                try {
                    log.info("Initialize open saml...");
                    DefaultBootstrap.bootstrap();
                } catch (ConfigurationException e) {
                    throw new FatalBeanException("Error invoking OpenSAML bootstrap", e);
                }
            }
        };
    }

    @Bean
    public SamlPrincipalFactory samlPrincipalFactory(SamlProperties samlProperties) {
        return LocalSamlPricipalFactory.builder().nameIdType(samlProperties.getNameIDType()).build();
    }
}
