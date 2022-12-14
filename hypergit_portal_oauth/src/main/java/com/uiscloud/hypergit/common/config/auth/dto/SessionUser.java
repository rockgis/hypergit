package com.uiscloud.hypergit.common.config.auth.dto;

import com.uiscloud.hypergit.user.domain.entity.OauthUser;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(OauthUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
