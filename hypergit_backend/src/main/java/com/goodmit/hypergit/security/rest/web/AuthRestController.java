package com.goodmit.hypergit.security.rest.web;

import com.goodmit.hypergit.security.rest.TokenProvider;
import com.goodmit.hypergit.security.rest.web.dto.TokenRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@Slf4j
//@Import({RestSecurityConfig.class})
@RequestMapping("/api")
public class AuthRestController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @Builder
    protected AuthRestController(@NonNull AuthenticationManager authenticationManager,@NonNull TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @RequestMapping(value = "/auth",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> auth(HttpServletRequest request
            , HttpServletResponse response
            , @RequestBody TokenRequest tokenRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getId(), tokenRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,token).build();
    }
}
