package com.goodmit.hypergit.global.security.authn.saml.sp;

import com.goodmit.hypergit.global.security.authn.saml.sp.consumer.AssertionConsumer;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.security.SecurityException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public class SamlAuthProvider implements AuthenticationProvider {

    private final AssertionConsumer assertionConsumer;

    @Builder
    protected SamlAuthProvider(AssertionConsumer assertionConsumer) {
        this.assertionConsumer = assertionConsumer;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthToken token = (PreAuthToken)authentication;
        SamlContext context = token.getContext();

        SAMLMessageContext messageContext = extractSAMLMessageContext(context.getRequest());

        Response samlResponse = (Response) messageContext.getInboundSAMLMessage();
        String statusCode = samlResponse.getStatus().getStatusCode().getValue();
        if (!StatusCode.SUCCESS_URI.equals(statusCode)) {
            log.error("SAML login failed. status code[{}]", statusCode);
            throw new AuthenticationServiceException("SAML response status fail, code[" + statusCode + "]");
        }

        UserDetails userDetails = assertionConsumer.consume(samlResponse);
        log.info("Login user[{}]", userDetails);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(String.valueOf(userDetails.getAuthorities())); // for test!!


//        UsernamePasswordAuthenticationToken resultToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername())
        UsernamePasswordAuthenticationToken resultToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),  null,authorities);
        resultToken.setAuthenticated(true);
        resultToken.setDetails(userDetails);


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthToken.class);
    }


    private SAMLMessageContext extractSAMLMessageContext(HttpServletRequest request) throws MessageDecodingException, SecurityException {
        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setInboundMessageTransport(new HttpServletRequestAdapter(request));
        HTTPPostDecoder decoder = new HTTPPostDecoder();
        decoder.decode(messageContext);
        return messageContext;
    }
}
