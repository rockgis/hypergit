package com.goodmit.hypergit.security.jwt;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("header ====> {}",request.getHeader(HEADER_AUTHORIZATION));
        String jwtToken = getJwtFromRequest(request);
//        String jwtToken = jwtTokenProvider.resolveToken(request);
        if(StringUtils.hasLength(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
//            log.info("=====>{}",authentication.getName());
            String name = jwtTokenProvider.getUserName(jwtToken);
            log.info("===>{}",name);
        }
        filterChain.doFilter(request,response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if(StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
