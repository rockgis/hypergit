package com.goodmit.hypergit.global.security.authn.saml.sp;

import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
public class SamlContext {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Builder
    protected SamlContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
