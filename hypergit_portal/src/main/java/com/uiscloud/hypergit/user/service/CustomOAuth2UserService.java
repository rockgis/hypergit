package com.uiscloud.hypergit.user.service;

import com.uiscloud.hypergit.user.domain.entity.OauthUser;
import com.uiscloud.hypergit.user.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.uiscloud.hypergit.user.domain.repository.OauthUserRepository;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        validateAttributes(oAuth2User.getAttributes());

        return null;
    }


    private void validateAttributes(Map<String, Object> attributes) {
        if (!attributes.containsKey("email")) {
            throw new IllegalArgumentException("서드파티의 응답에 email이 존재하지 않습니다!!!");
        }
    }
}