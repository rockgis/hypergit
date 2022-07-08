package com.goodmit.hypergit.global.security.authn.saml.sp.filter;

import com.goodmit.hypergit.global.security.authn.saml.sp.ContextProvider;
import com.goodmit.hypergit.global.security.authn.saml.sp.PreAuthToken;
import com.goodmit.hypergit.global.security.authn.saml.sp.SamlContext;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AssertionConsumerFilter extends AbstractAuthenticationProcessingFilter {

    private ContextProvider samlContextProvider;

    @Builder
    protected AssertionConsumerFilter(String defaultFilterProcessesUrl,ContextProvider samlContextProvider) {
        super(defaultFilterProcessesUrl);
        this.samlContextProvider = samlContextProvider;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        SamlContext context = samlContextProvider.getLocalContext(request, response);
        PreAuthToken token = PreAuthToken.builder()
                .context(context).build();
        return getAuthenticationManager().authenticate(token);

    }


}
