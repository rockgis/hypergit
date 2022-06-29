package com.goodmit.hypergit.identity.saml.config.properties;

import com.goodmit.hypergit.identity.saml.metadata.BindingType;
import com.goodmit.hypergit.util.NetUtil;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "saml.idp")
public class SamlProperties {

    private final String authUrl;
    private final String entityId;
    private final int expired;
    private final int clockSkew;
    private final String nameIDType;
    private final String bindingAddress;

    private final KeyProperties key;
    private final List<BindingType> ssoBindings;
    private final List<BindingType> sloBindings;
    protected SamlProperties(String authUrl, @NonNull String entityId, int expired,
                             int clockSkew, String nameIDType, String bindingAddress, KeyProperties key,
                             @NonNull List<BindingType> ssoBindings, List<BindingType> sloBindings) {

        this.authUrl = authUrl;
        this.entityId = entityId;
        this.expired = expired;
        this.clockSkew = clockSkew;
        this.nameIDType = nameIDType;
        this.key = key;

        this.ssoBindings = ssoBindings;
        this.sloBindings = Objects.isNull(sloBindings)? Collections.emptyList():sloBindings;
        this.bindingAddress = StringUtils.hasText(bindingAddress)?bindingAddress: NetUtil.getLocalAddress();
    }
}
