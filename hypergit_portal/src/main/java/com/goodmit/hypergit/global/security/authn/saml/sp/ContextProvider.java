package com.goodmit.hypergit.global.security.authn.saml.sp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextProvider {
    public SamlContext getLocalContext(HttpServletRequest request, HttpServletResponse response) {
        return SamlContext.builder()
                .request(request)
                .response(response)
                .build();
    }
}
