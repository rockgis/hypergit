package com.goodmit.hypergit.global.security.authn.saml.filter;

import com.goodmit.hypergit.global.security.authn.properties.SAMLProperties;
import com.goodmit.hypergit.global.security.authn.saml.SamlBuilder;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.encoding.BaseSAML2MessageEncoder;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.saml.util.SAMLUtil;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class EntryPoint extends GenericFilterBean implements AuthenticationEntryPoint {
    private SAMLProperties samlProperties;
    private String localAuth;

    private BaseSAML2MessageEncoder encoder;

    @Builder
    protected EntryPoint(SAMLProperties samlProperties,String localAuth, BaseSAML2MessageEncoder encoder) {
        this.samlProperties = samlProperties;
        this.localAuth = localAuth;
        this.encoder = encoder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        if(isLocalAuth(fi.getRequest())) {
            chain.doFilter(request,response);
            return;
        }
        log.info("in enrty point : {}",fi.getRequest().getRequestURI());
        commence(fi.getRequest(),fi.getResponse(),null);
    }

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject> context = new BasicSAMLMessageContext<>();

        HttpServletResponseAdapter transport = new HttpServletResponseAdapter(response, false);
        context.setOutboundMessageTransport(transport);

        Endpoint endpoint = SamlBuilder.buildEndpoint(samlProperties.getIpdUrl());
        context.setPeerEntityEndpoint(endpoint);

        Issuer issuer = SamlBuilder.buildIssuer(samlProperties.getEntityId());
        AuthnRequest authnRequest = SamlBuilder.buildAuthnRequest(
                samlProperties.getAcsLocation(),
                SAMLConstants.SAML2_REDIRECT_BINDING_URI,
                issuer
        );

        context.setOutboundSAMLMessage(authnRequest);
        encoder.encode(context);
    }

    private boolean isLocalAuth(HttpServletRequest request) {
            return PathRequest.toStaticResources().atCommonLocations().matches(request)
                || SAMLUtil.processFilter(localAuth, request) || SAMLUtil.processFilter("/static",request);
    }

}
