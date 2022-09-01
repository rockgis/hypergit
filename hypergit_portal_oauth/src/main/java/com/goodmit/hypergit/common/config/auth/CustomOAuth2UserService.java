package com.goodmit.hypergit.common.config.auth;

import com.goodmit.hypergit.common.config.auth.dto.SessionUser;
import com.goodmit.hypergit.user.domain.entity.OauthUser;
import com.goodmit.hypergit.common.config.auth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.goodmit.hypergit.user.domain.repository.OauthUserRepository;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OauthUserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("OAuth2User bean Start");
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.info("OAuth2User userRequest" + userRequest.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        OauthUser oauthUser = saveOrUpdate(attributes);

        log.info("OAuth2User attributes" + attributes.toString());

        httpSession.setAttribute("user", new SessionUser(oauthUser));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(oauthUser.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private OauthUser saveOrUpdate(OAuthAttributes attributes) {
        OauthUser oauthUser = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(oauthUser);
    }

}