package com.goodmit.hypergit.global.security.authn.ldap;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class LdapAuthenticationProvider implements AuthenticationProvider{

    private final LdapContextSource contextSource;
    private LdapTemplate ldapTemplate;

    @PostConstruct
    private void initContext() {
        log.info("{}", String.join(",",contextSource.getUrls()));
        ldapTemplate = new LdapTemplate(contextSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Filter filter = new EqualsFilter("uid",authentication.getName());
        boolean authenticate = ldapTemplate.authenticate(
                LdapUtils.emptyLdapName(),
                filter.encode(),
                authentication.getCredentials().toString());

        if(!authenticate) {
            return null;
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(authentication.getName()
                ,authentication.getCredentials().toString(),
                grantedAuthorities);

        return new UsernamePasswordAuthenticationToken(userDetails,authentication.getCredentials().toString(),grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
