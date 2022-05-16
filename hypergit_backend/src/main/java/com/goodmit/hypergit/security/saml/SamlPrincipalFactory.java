package com.goodmit.hypergit.security.saml;

import com.goodmit.hypergit.security.saml.dao.SamlAttribute;
import com.goodmit.hypergit.security.saml.dao.SamlPrincipal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.LogoutRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml.context.SAMLMessageContext;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SamlPrincipalFactory {

    @Getter
    private final String nameIdType;

    public SamlPrincipal createAuthSamlPrincipal(SAMLMessageContext messageContext, Authentication authentication) {
        AuthnRequest authnRequest = (AuthnRequest) messageContext.getInboundSAMLMessage();
        List<SamlAttribute> attributes = createAttributes(authentication);
        return SamlPrincipal.builder()
                .nameID(authentication.getName())
                .nameIDType(nameIdType)
                .attributes(attributes)
                .serviceProviderEntityID(authnRequest.getIssuer().getValue())
                .requestID(authnRequest.getID())
                .assertionConsumerServiceUrl(authnRequest.getAssertionConsumerServiceURL())
                .relayState(messageContext.getRelayState())
                .build();
    }

    public SamlPrincipal createLogoutSamlPrincipal(SAMLMessageContext messageContext) {
        LogoutRequest logoutRequest = (LogoutRequest)messageContext.getInboundSAMLMessage();
        return SamlPrincipal.builder()
                .nameID(logoutRequest.getNameID().getValue())
                .nameIDType(logoutRequest.getNameID().getFormat())
                .requestID(logoutRequest.getID())
                .serviceProviderEntityID(logoutRequest.getIssuer().getValue())
                .relayState(messageContext.getRelayState())
                .build();
    }

    protected abstract List<SamlAttribute> createAttributes(Authentication authentication);
}
