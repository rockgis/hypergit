package com.uiscloud.hypergit.policymng.dto;

import com.uiscloud.hypergit.policymng.domain.entity.Gittf0003Entity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittf0003Dto {

    private Long id; //'등록순번'

    private int urCk ;//UR_CK INT COMMENT '인증허용 횟수',

    private String urDay ;//'SE_TF     VARCHAR(1) COMMENT '장기미사용 지준일 ',

    private String samlUrl; //  SAML_URL VARCHAR(50) COMMENT 'SAML 시스템 연결정보',

    private String samlPass; // SAML_PASS VARCHAR(50) COMMENT 'SAML 리시스템 PASS',

    private String rangerUrl ;//RANGER_URL VARCHAR(50) COMMENT 'RANGER 시스템 연결정보',

    private String rangerAuth; //RANGER_PASS VARCHAR(50) COMMENT 'RANGER 시스템 PASS'



    public Gittf0003Entity toEntity(){
        Gittf0003Entity gitta0003Entity = Gittf0003Entity.builder()
                .id(id)
                .urCk(urCk)
                .urDay(urDay)
                .samlUrl(samlUrl)
                .samlPass(samlPass)
                .rangerUrl(rangerUrl)
                .rangerAuth(rangerAuth)
                .build();
        return gitta0003Entity;
    }

    @Builder
    public Gittf0003Dto(Long id, int urCk, String urDay, String samlUrl, String samlPass, String rangerUrl, String rangerAuth) {
        this.id = id;
        this.urCk = urCk;
        this.urDay = urDay;
        this.samlUrl = samlUrl;
        this.samlPass = samlPass;
        this.rangerUrl = rangerUrl;
        this.rangerAuth = rangerAuth;
    }
}
