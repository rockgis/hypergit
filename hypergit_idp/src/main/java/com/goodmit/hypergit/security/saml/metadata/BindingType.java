package com.goodmit.hypergit.security.saml.metadata;

import lombok.Getter;
import org.opensaml.common.xml.SAMLConstants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BindingType {
    ARTIFACT(SAMLConstants.SAML2_ARTIFACT_BINDING_URI),
    POST(SAMLConstants.SAML2_POST_BINDING_URI),
    REDIRECT(SAMLConstants.SAML2_REDIRECT_BINDING_URI),
    PAOS(SAMLConstants.SAML2_PAOS_BINDING_URI),
    SOAP(SAMLConstants.SAML2_SOAP11_BINDING_URI);
    @Getter
    private final String bindingUri;

    BindingType(String bindingUri) {
        this.bindingUri = bindingUri;
    }

    static List<String> getAllBindingUriList() {
        return Arrays.stream(values()).map(BindingType::getBindingUri).collect(Collectors.toList());
    }
}
