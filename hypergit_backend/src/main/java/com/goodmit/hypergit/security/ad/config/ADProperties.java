package com.goodmit.hypergit.security.ad.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@ConstructorBinding
@ConfigurationProperties(prefix = "ad")
public class ADProperties {

    private final String url;
    private final String domain;
    private final String rootDn;


}
