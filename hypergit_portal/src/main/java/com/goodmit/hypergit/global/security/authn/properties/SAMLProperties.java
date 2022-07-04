package com.goodmit.hypergit.global.security.authn.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "saml")
public class SAMLProperties {

    private final String entityId;
    private final String ipdUrl;
    private final String acs;

    protected SAMLProperties(String entityId, String ipdUrl, String acs) {
        this.entityId = entityId;
        this.ipdUrl = ipdUrl;
        this.acs = acs;
    }
}
