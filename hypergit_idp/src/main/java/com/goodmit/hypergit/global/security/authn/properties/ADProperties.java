package com.goodmit.hypergit.global.security.authn.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "ad")
public class ADProperties {

    private String url;
    private String domain;
    private String rootDn;

    protected ADProperties(String url, String domain, String rootDn) {
        this.url = url;
        this.domain = domain;
        this.rootDn = rootDn;
    }
}
