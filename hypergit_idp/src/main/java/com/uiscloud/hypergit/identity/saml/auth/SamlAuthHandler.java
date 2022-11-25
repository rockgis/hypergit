package com.uiscloud.hypergit.identity.saml.auth;

import com.uiscloud.hypergit.identity.saml.auth.dao.SamlPrincipal;
import com.uiscloud.hypergit.identity.saml.config.properties.SamlProperties;
import com.uiscloud.hypergit.identity.saml.application.KeyService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.binding.encoding.HTTPPostSimpleSignEncoder;
import org.opensaml.saml2.core.*;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;
import org.opensaml.ws.security.provider.StaticSecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;
import org.springframework.security.saml.SAMLConstants;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.SingleLogoutProfile;
import org.springframework.security.saml.websso.SingleLogoutProfileImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SamlAuthHandler {

    private SamlProperties samlProperties;
    private SAMLMessageDecoder decoder;
    private SAMLMessageEncoder encoder;
    private List<ValidatorSuite> validatorSuites;
    private SecurityPolicyResolver resolver;

    private KeyService keyService;

    @Builder
    protected SamlAuthHandler(SamlProperties samlProperties, KeyService keyService) throws XMLParserException {
        this.samlProperties = samlProperties;
        this.keyService = keyService;
        initDecoder();
        initEncoder();
        initPolicyResolver();
        initValidatorSuites();
        initSaml();
    }

    public SAMLMessageContext extractSAMLMessageContext(HttpServletRequest request, HttpServletResponse response) throws MessageDecodingException, SecurityException, ValidationException {
        SAMLMessageContext messageContext = decodeMessageContext(request,response);
        validateAuthRequest(messageContext);
        return messageContext;
    }

    public void sendAuthnResponse(SamlPrincipal principal, HttpServletResponse response)
            throws SecurityException, MarshallingException, SignatureException, MessageEncodingException {
        Status status = SamlBuilder.buildStatus(StatusCode.SUCCESS_URI);
        Credential signingCredential = keyService.resolveCredential();
        Issuer issuer = SamlBuilder.buildIssuer(samlProperties.getEntityId());

        Response authResponse = SamlBuilder.buildSAMLObject(Response.class,Response.DEFAULT_ELEMENT_NAME);
        authResponse.setIssuer(issuer);
        authResponse.setID(SamlBuilder.randomSAMLId());
        authResponse.setIssueInstant(DateTime.now());
        authResponse.setInResponseTo(principal.getRequestID());

        Assertion assertion = SamlBuilder.buildAssertion(principal, status, samlProperties.getEntityId(),keyService.getPassphrase());
        SamlBuilder.signAssertion(assertion,signingCredential);

        authResponse.getAssertions().add(assertion);
        authResponse.setDestination(principal.getAssertionConsumerServiceUrl());
        authResponse.setStatus(status);

        sendResponse(authResponse,response,principal,signingCredential);
    }

    public void sendLogoutResponse(SAMLMessageContext samlMessageContext) throws SAMLException, MessageEncodingException, MetadataProviderException {
        SingleLogoutProfile singleLogoutProfile = new SingleLogoutProfileImpl();
        singleLogoutProfile.sendLogoutResponse(samlMessageContext,StatusCode.SUCCESS_URI,"");

    }

    public void sendLogoutResponse(SamlPrincipal principal,HttpServletResponse response) throws SecurityException, MarshallingException, SignatureException, MessageEncodingException {
        Status status = SamlBuilder.buildStatus(StatusCode.SUCCESS_URI);
        Credential signingCredential = keyService.resolveCredential();
        Issuer issuer = SamlBuilder.buildIssuer(samlProperties.getEntityId());

        LogoutResponse logoutResponse = SamlBuilder.buildSAMLObject(LogoutResponse.class,LogoutResponse.DEFAULT_ELEMENT_NAME);
        logoutResponse.setIssuer(issuer);
        logoutResponse.setID(SamlBuilder.randomSAMLId());
        logoutResponse.setIssueInstant(DateTime.now());
        logoutResponse.setDestination(principal.getAssertionConsumerServiceUrl());
        logoutResponse.setStatus(status);

        SamlBuilder.signAssertion(logoutResponse,signingCredential);
        sendResponse(logoutResponse,response,principal,signingCredential);


    }

    private void sendResponse(SAMLObject samlObject , HttpServletResponse response, SamlPrincipal principal, Credential signingCredential) throws MessageEncodingException {
        Endpoint endpoint = SamlBuilder.buildSAMLObject(Endpoint.class, SingleSignOnService.DEFAULT_ELEMENT_NAME);
        endpoint.setLocation(principal.getAssertionConsumerServiceUrl());

        HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response,false);
        BasicSAMLMessageContext<SAMLObject, SAMLObject, SAMLObject> messageContext = new BasicSAMLMessageContext<>();

        messageContext.setOutboundMessageTransport(outTransport);
        messageContext.setPeerEntityEndpoint(endpoint);
        messageContext.setOutboundSAMLMessage(samlObject);
        messageContext.setOutboundSAMLMessageSigningCredential(signingCredential);
        messageContext.setOutboundMessageIssuer(samlProperties.getEntityId());
        messageContext.setRelayState(principal.getRelayState());
        encoder.encode(messageContext);
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
            validateSuit.validate(messageContext.getInboundMessage());
        }
    }

    private void initDecoder() throws XMLParserException {
        StaticBasicParserPool parserPool = new StaticBasicParserPool();
        parserPool.initialize();
        decoder = new HTTPRedirectDeflateDecoder(parserPool);
    }

    private void initEncoder() {
        String encoderTemplate = "/templates/saml2-post-binding.vm";
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

    private void initSaml() {
        SingleLogoutProfile singleLogoutProfile = new SingleLogoutProfileImpl();
    }
}
