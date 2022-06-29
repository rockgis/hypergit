package com.goodmit.hypergit.global.security.rest.application.dto;

import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ConstructorBinding
public class TokenRequest {
    @NonNull private String id;
    @NonNull private String password;
}
