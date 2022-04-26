package com.goodmit.hypergit.security.saml;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.opensaml.Configuration;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.binding.encoding.HTTPPostSimpleSignEncoder;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;
import org.opensaml.ws.security.provider.StaticSecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;
import org.springframework.security.saml.SAMLConstants;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.util.VelocityFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SamlAuthHandler {
    private SamlProperties samlProperties;
    private SAMLMessageDecoder decoder;
    private SAMLMessageEncoder encoder;
    private List<ValidatorSuite> validatorSuites;
    private SecurityPolicyResolver resolver;

    public SamlAuthHandler(SamlProperties samlProperties) throws XMLParserException {
        this.samlProperties = samlProperties;
        initDecoder();
        initEncoder();
        initPolicyResolver();
        initValidatorSuites();
    }

    public SAMLMessageContext extractSAMLMessageCntext(HttpServletRequest request, HttpServletResponse response) throws MessageDecodingException, SecurityException, ValidationException {
        SAMLMessageContext messageContext = decodeMessageContext(request,response);
        validateAuthRequest(messageContext);
        return messageContext;
    }

    private SAMLMessageContext decodeMessageContext(HttpServletRequest request, HttpServletResponse response) throws MessageDecodingException, SecurityException {
        SAMLMessageContext messageContext = new SAMLMessageContext();
        HttpServletRequestAdapter inTransport = new HttpServletRequestAdapter(request);
        HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response,request.isSecure());
        request.setAttribute(SAMLConstants.LOCAL_CONTEXT_PATH,request.getContextPath());
        messageContext.setInboundMessageTransport(inTransport);
        messageContext.setOutboundMessageTransport(outTransport);
        messageContext.setSecurityPolicyResolver(resolver);
        decoder.decode(messageContext);
        return messageContext;
    }

    private void validateAuthRequest(SAMLMessageContext messageContext) throws ValidationException {
        for (ValidatorSuite validateSuit : validatorSuites) {
            validateSuit.validate((AuthnRequest)messageContext.getInboundMessage());
        }
    }

    private void initDecoder() throws XMLParserException {
        StaticBasicParserPool parserPool = new StaticBasicParserPool();
        parserPool.initialize();
        decoder = new HTTPRedirectDeflateDecoder(parserPool);
    }

    private void initEncoder() {
        String encoderTemplate = "/templates/saml2-post-binding.tm";
        boolean signXMLProtocolMessage = true;

        encoder = new HTTPPostSimpleSignEncoder(
                VelocityFactory.getEngine(),
                encoderTemplate,
                signXMLProtocolMessage
        );
    }

    private void initPolicyResolver() {
        BasicSecurityPolicy securityPolicy = new BasicSecurityPolicy();
        securityPolicy.getPolicyRules()
                .add(new IssueInstantRule(samlProperties.getClockSkew(),samlProperties.getExpired()));
        resolver = new StaticSecurityPolicyResolver(securityPolicy);
    }

    private void initValidatorSuites() {
        validatorSuites = Stream.of("saml2-core-schema-validator","saml2-core-spec-validator")
                .map(Configuration::getValidatorSuite)
                .collect(Collectors.toList());
    }
}
