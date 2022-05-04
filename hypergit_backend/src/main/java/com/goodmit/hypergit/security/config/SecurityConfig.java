package com.goodmit.hypergit.security.config;

import com.goodmit.hypergit.security.saml.SamlConfiguration;
import com.goodmit.hypergit.security.saml.SamlProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.SessionCookieConfig;

@Slf4j
@Import({SamlConfiguration.class})
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EnableWebSecurity
@EnableConfigurationProperties(value = {SamlProperties.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OncePerRequestFilter samlResponseFilter;

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
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico","/*.js");
    }

    /*
     * TODO: change userDetailService with openLdap
     */
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails test = User
//                .withUsername("test")
//                .password(passwordEncoder.encode("test"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User
//                .withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(test,admin);
//
//    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
            sessionCookieConfig.setName("idp.session");
            sessionCookieConfig.setHttpOnly(true);
        };
    }


}
