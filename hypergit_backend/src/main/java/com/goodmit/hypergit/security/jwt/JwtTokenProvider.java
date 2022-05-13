package com.goodmit.hypergit.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtTokenProvider {
    private static final String SECRET_KEY="secretKey";
    private static final long TOKEN_VALID_TIME=30*60*1000L;

//    private final UserDetailsService userDetailsService;

    public String createToken(String pk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(pk);
        claims.put("roles",roles);
        DateTime now = DateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now.toDate())
                .setExpiration(now.plus(TOKEN_VALID_TIME).toDate())
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
//        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
//    }

    public String getUserName(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request,"X-AUTH-TOKEN");
        return Objects.isNull(cookie)?null:cookie.getValue();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(DateTime.now().toDate());
    }

    private String getUserPk(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
