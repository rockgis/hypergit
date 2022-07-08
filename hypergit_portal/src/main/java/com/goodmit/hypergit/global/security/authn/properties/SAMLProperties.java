package com.goodmit.hypergit.global.security.authn.properties;

import com.goodmit.hypergit.common.util.NetUtil;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.web.csrf.CsrfFilter;
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

        if(acsUrl.contains("${server.address}")) {
            acsUrl = StringUtils.replace(acsUrl,"${server.address}",NetUtil.getLocalAddress());
        }
        this.acsUrl = acsUrl;
        CsrfFilter g;
    }

    public String getAcsLocation() {
        StringBuffer stringBuffer = new StringBuffer();
        if(!acsUrl.startsWith("http://")) {
            stringBuffer.append("http://");
        }
        return stringBuffer
                .append(acsUrl)
                .append(acs)
                .toString();
    }
}
