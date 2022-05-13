package com.goodmit.hypergit.security.saml;

import com.goodmit.hypergit.security.saml.config.SamlProperties;
import com.goodmit.hypergit.security.saml.dao.SamlPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.LogoutRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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

    public SamlResponseFilter(@NonNull SamlProperties samlProperties,@NonNull SamlAuthHandler samlAuthHandler,@NonNull SamlPrincipalFactory samlPrincipalFactory){
        this.samlProperties = samlProperties;
        this.samlAuthHandler = samlAuthHandler;
        this.samlPrincipalFactory = samlPrincipalFactory;
    }


    //process saml url
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        log.info("-======> saml filter");
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

        QName reqName = messageContext.getInboundSAMLMessage().getElementQName();

        if(reqName.getLocalPart().equals(LogoutRequest.DEFAULT_ELEMENT_LOCAL_NAME)){
            log.info("logout process");
        } else {
            SamlPrincipal samlPrincipal = samlPrincipalFactory.createSamlPrincipal(messageContext,authentication);
            samlAuthHandler.sendAuthnResponse(samlPrincipal,response);
        }

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
