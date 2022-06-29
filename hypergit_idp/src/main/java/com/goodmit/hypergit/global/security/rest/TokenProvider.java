package com.goodmit.hypergit.global.security.rest;

import com.goodmit.hypergit.identity.saml.key.KeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenProvider {
    private final KeyService keyService;

    private final UserDetailsService userService;

    private final static long ACCESS_TOKEN_VALID_TIME=2 * 60 * 60 * 1000L;

    public String generateToken(@NonNull Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
        DateTime now = DateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now.toDate())
                .setExpiration(now.plus(ACCESS_TOKEN_VALID_TIME).toDate())
                .signWith(SignatureAlgorithm.HS256,keyService.getPassphrase())
                .compact();
    }

    public Authentication getAuthentication(@NonNull String token) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        UserDetails userDetails = userService.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public boolean validateToken(@NonNull String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return !claims.getExpiration().before(DateTime.now().toDate());
    }

    private Claims getClaims(@NonNull String token) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return Jwts.parser().setSigningKey(keyService.getPassphrase()).parseClaimsJws(token).getBody();
    }

    private String getSubject(@NonNull String token) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return getClaims(token).getSubject();
    }

}
