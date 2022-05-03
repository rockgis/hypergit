package com.goodmit.hypergit.security.config;

import com.goodmit.hypergit.security.saml.SamlConfiguration;
import com.goodmit.hypergit.security.saml.SamlProperties;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.SessionCookieConfig;

@Slf4j
@Import({SamlConfiguration.class})
@EnableWebSecurity
@EnableConfigurationProperties(value = {SamlProperties.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OncePerRequestFilter samlResponseFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .and()
                .addFilterAfter(samlResponseFilter, FilterSecurityInterceptor.class);
//                .loginPage("/login").permitAll()
//                .failureUrl("/login?error=true").permitAll()
//                .and()
//                .logout()

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


}
