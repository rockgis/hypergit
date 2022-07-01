package com.goodmit.hypergit.repository.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginAudit {
    @Id
    private long idx;
    private String name;
    private String remote;
    private boolean result;
    @Nullable
    private String reason;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime time;

    @Builder
    protected LoginAudit(String name, String remote, boolean result, @Nullable String reason, LocalDateTime time) {
        this.name = name;
        this.remote = remote;
        this.result = result;
        this.reason = reason;
        this.time = time;
    }
}
