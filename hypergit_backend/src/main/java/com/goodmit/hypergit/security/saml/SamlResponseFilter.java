package com.goodmit.hypergit.security.saml;

import com.goodmit.hypergit.security.saml.dao.SamlPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.context.SAMLMessageContext;
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

    private SamlPrincipalFactory samlPrincipalFactory;

    public SamlResponseFilter(@NonNull SamlProperties samlProperties,@NonNull SamlAuthHandler samlAuthHandler,@NonNull SamlPrincipalFactory samlPrincipalFactory){
        this.samlProperties = samlProperties;
        this.samlAuthHandler = samlAuthHandler;
        this.samlPrincipalFactory = samlPrincipalFactory;
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
        SamlPrincipal samlPrincipal = samlPrincipalFactory.createSamlPrincipal(messageContext, authentication);
        samlAuthHandler.sendAuthnResponse(samlPrincipal,response);
    }

    private boolean isSamlAuthJob(HttpServletRequest request,Authentication authentication) {
        if(!request.getRequestURI().startsWith(samlProperties.getAuthUrl())) {
            return false;
        }

        if(Objects.isNull(authentication) ||
                authentication instanceof AnonymousAuthenticationToken ||
                !authentication.isAuthenticated()) {
            return false;
        }

        log.info("principal : {}",authentication.getPrincipal().toString());
        return true;
    }
}
