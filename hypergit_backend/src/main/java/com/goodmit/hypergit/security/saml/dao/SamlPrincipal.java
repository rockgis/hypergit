package com.goodmit.hypergit.security.saml.dao;

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
    @NonNull
    private List<SamlAttribute> attributes;
    @NonNull
    private String serviceProviderEntityID;
    @NonNull
    private String requestID;
    @NonNull
    private String assertionConsumerServiceUrl;
    @NonNull
    private String relayState;

    @Builder
    protected SamlPrincipal(@NonNull String nameID, @NonNull String nameIDType,
                            @NonNull List<SamlAttribute> attributes, @NonNull String serviceProviderEntityID,
                            @NonNull String requestID, @NonNull String assertionConsumerServiceUrl,
                            @NonNull String relayState) {

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
