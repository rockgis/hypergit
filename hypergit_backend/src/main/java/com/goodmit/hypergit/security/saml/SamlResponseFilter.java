package com.goodmit.hypergit.security.saml;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamlResponseFilter extends OncePerRequestFilter {

    private SamlProperties samlProperties;
    private SamlAuthHandler samlAuthHandler;

    public SamlResponseFilter(SamlProperties samlProperties,SamlAuthHandler samlAuthHandler){
        this.samlProperties = samlProperties;
        this.samlAuthHandler = samlAuthHandler;
    }

    //process saml url
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isSamlAuthJob(request,authentication)) {
            filterChain.doFilter(request,response);
            return;
        }

        SAMLMessageContext messageContext = samlAuthHandler.extractSAMLMessageCntext(request, response);
        if(!messageContext.getInboundMessageIssuer().equals(samlProperties.getEntityId())) {
            filterChain.doFilter(request,response);
            return;
        }

        /*
         * TODO : implements saml auth
         */
    }

    private boolean isSamlAuthJob(HttpServletRequest request,Authentication authentication) {
        if(!request.getRequestURI().startsWith(samlProperties.getAuthUrl())) {
            return false;
        }

        if(Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }

        log.info("principal : {}",authentication.getPrincipal().toString());
        return true;
    }
}
