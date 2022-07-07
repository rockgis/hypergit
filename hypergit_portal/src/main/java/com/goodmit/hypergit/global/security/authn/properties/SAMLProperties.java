package com.goodmit.hypergit.global.security.authn.properties;

import com.goodmit.hypergit.common.util.NetUtil;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.util.StringUtils;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "authentication.saml")
public class SAMLProperties {

    private final String entityId;
    private final String ipdUrl;
    private final String acs;
    private final String acsUrl;

    protected SAMLProperties(String entityId, String ipdUrl, String acs,String acsUrl) {
        this.entityId = entityId;
        this.ipdUrl = ipdUrl;
        this.acs = acs;
        this.acsUrl = StringUtils.hasText(acsUrl)?acsUrl: NetUtil.getLocalAddress();
    }

    public String getAcsLocation() {
        StringBuffer stringBuffer = new StringBuffer("http");
        return stringBuffer
                .append(acsUrl)
                .append("/")
                .append(acs)
                .toString();
    }
}
