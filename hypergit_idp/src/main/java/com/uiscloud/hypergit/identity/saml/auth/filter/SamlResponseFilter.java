package com.uiscloud.hypergit.identity.saml.auth.filter;

import com.uiscloud.hypergit.identity.saml.auth.SamlAuthHandler;
import com.uiscloud.hypergit.identity.saml.auth.SamlPrincipalFactory;
import com.uiscloud.hypergit.identity.saml.auth.dao.SamlPrincipal;
import com.uiscloud.hypergit.identity.saml.config.properties.SamlProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml2.core.LogoutRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamlResponseFilter extends OncePerRequestFilter {

    private SamlProperties samlProperties;
    private SamlAuthHandler samlAuthHandler;

    private SamlPrincipalFactory samlPrincipalFactory;

    @Builder
    protected SamlResponseFilter(@NonNull SamlProperties samlProperties,
                                 @NonNull SamlAuthHandler samlAuthHandler,
                                 @NonNull SamlPrincipalFactory samlPrincipalFactory){
        this.samlProperties = samlProperties;
        this.samlAuthHandler = samlAuthHandler;
        this.samlPrincipalFactory = samlPrincipalFactory;
    }

    //process saml url
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        log.info("START SAML AUTHENTICATION : {}",request.getMethod());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SAMLMessageContext messageContext = samlAuthHandler.extractSAMLMessageContext(request, response);
        if(!messageContext.getInboundMessageIssuer().equals(samlProperties.getEntityId())) {
            filterChain.doFilter(request,response);
            return;
        }

        QName reqName = messageContext.getInboundSAMLMessage().getElementQName();
        if(reqName.getLocalPart().equals(LogoutRequest.DEFAULT_ELEMENT_LOCAL_NAME)){
            //TODO : after hue ,cdsw ,cm saml logout spec check
            log.info("logout process : {} , {}",request.getRequestURI(),request.getUserPrincipal().toString());
//            SamlPrincipal samlPrincipal = samlPrincipalFactory.createLogoutSamlPrincipal(messageContext);
//            samlAuthHandler.sendLogoutResponse(samlPrincipal,response);
//            samlAuthHandler.sendLogoutResponse(messageContext);
//            SecurityContextHolder.clearContext();
            response.sendRedirect("/login");
        } else {
            SamlPrincipal samlPrincipal = samlPrincipalFactory.createAuthSamlPrincipal(messageContext,authentication);
            samlAuthHandler.sendAuthnResponse(samlPrincipal,response);
        }
        log.info("SEND SAML RESPONSE");

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !isSamlAuthJob(request,SecurityContextHolder.getContext().getAuthentication());
    }

    private boolean isSamlAuthJob(HttpServletRequest request, Authentication authentication) {
        if(!request.getRequestURI().startsWith(samlProperties.getAuthUrl())) {
            return false;
        }

        return !Objects.isNull(authentication) &&
                !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated();
    }
}
