package com.goodmit.hypergit.security.saml;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamlResponseFilter extends OncePerRequestFilter {

    private String authUrl;

    public SamlResponseFilter(String authUrl){
        this.authUrl = authUrl;
    }

    //process saml url
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isSamlAuthJob(request,authentication)) {
            filterChain.doFilter(request,response);
            return;
        }
        /*
         * TODO : implements saml auth
         */

    }

    private boolean isSamlAuthJob(HttpServletRequest request,Authentication authentication) {
        if(!request.getRequestURI().startsWith(authUrl)) {
            return false;
        }

        if(Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }

        log.info("principal : {}",authentication.getPrincipal().toString());
        return true;
    }
}
