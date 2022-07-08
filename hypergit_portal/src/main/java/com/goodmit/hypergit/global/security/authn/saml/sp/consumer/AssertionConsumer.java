package com.goodmit.hypergit.global.security.authn.saml.sp.consumer;

import org.opensaml.saml2.core.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface AssertionConsumer {
    UserDetails consume(Response samlResponse) throws AuthenticationException;
}
