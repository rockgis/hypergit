package com.goodmit.hypergit.global.security.authn;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HAuthnProvider implements AuthenticationProvider {
    private final AuthenticationProvider adAuthProvider;

    private List<UserDetails> users;

    @Builder
    private HAuthnProvider(@NonNull ActiveDirectoryLdapAuthenticationProvider adAuthProvider) {
        this.adAuthProvider = adAuthProvider;
        users = new ArrayList<>();
        users.add(User.builder().roles("ADMIN").username("samltest").password("git08021!").build());
        users.add(User.builder().roles("USER").username("saml").password("git08021!").build());

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authn = adAuthProvider.authenticate(authentication);
        UserDetails user = users.stream().filter(userDetails -> userDetails.getUsername().equals(authn.getName())
                && userDetails.getPassword().equals(authn.getCredentials()))
                .findFirst().orElse(null);


        List<String> authorizes = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        AuthorityUtils.createAuthorityList(authorizes.toArray(new String[authorizes.size()]));

        authn.getAuthorities();
        return authn;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return adAuthProvider.supports(authentication);
    }
}
