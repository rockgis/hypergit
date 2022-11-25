package com.goodmit.hypergit.identity.saml.auth.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.security.Principal;
import java.util.List;

@Getter
public class SamlPrincipal implements Principal {

    @NonNull
    private String nameID;
    @NonNull
    private String nameIDType;
    private List<SamlAttribute> attributes;
    private String serviceProviderEntityID;
    @NonNull
    private String requestID;
    private String assertionConsumerServiceUrl;
    private String relayState;

    @Builder
    protected SamlPrincipal(@NonNull String nameID, @NonNull String nameIDType, List<SamlAttribute> attributes,
                            String serviceProviderEntityID, String requestID, String assertionConsumerServiceUrl, String relayState) {

        this.nameID = nameID;
        this.nameIDType = nameIDType;
        this.attributes = attributes;
        this.serviceProviderEntityID = serviceProviderEntityID;
        this.requestID = requestID;
        this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
        this.relayState = relayState;
    }

    @Override
    public String getName() {
        return nameID;
    }
}
