package com.goodmit.hypergit.user.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    ADMIN("ROLE_ADMIN", "꽌리자"),
    USER("ROLE_USER", "일반 사용자");


    private final String key;
    private final String title;

}
