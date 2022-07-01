package com.goodmit.hypergit.repository.entity;

import lombok.Getter;

public enum Role {
    ROLE_ADMIN,
    ROLE_USER;
    @Getter
    private String shortName;
    Role() {
        shortName = name().substring(name().indexOf("_")+1);
    }
}
