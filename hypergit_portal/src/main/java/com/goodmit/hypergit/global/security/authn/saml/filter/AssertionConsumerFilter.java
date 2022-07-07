package com.goodmit.hypergit.global.security.authn.saml.filter;

import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.saml.context.SAMLContextProvider;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AssertionConsumerFilter extends AbstractAuthenticationProcessingFilter {

    private SAMLContextProvider samlContextProvider;

    @Builder
    protected AssertionConsumerFilter(String defaultFilterProcessesUrl,SAMLContextProvider samlContextProvider) {
        super(defaultFilterProcessesUrl);
        this.samlContextProvider = samlContextProvider;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        SAMLMessageContext context = samlContextProvider.getLocalEntity(request, response);
        return null;
    }
}
