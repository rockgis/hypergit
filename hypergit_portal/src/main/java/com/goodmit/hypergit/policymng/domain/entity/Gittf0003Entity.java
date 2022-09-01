package com.goodmit.hypergit.policymng.domain.entity;

//'사용자관리'
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTF0003")
public class Gittf0003Entity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(columnDefinition ="int" )
    private int urCk ;//UR_CK INT COMMENT '인증허용 횟수',

    @Column(columnDefinition ="char" , length = 2)
    private String urDay ;//'SE_TF     VARCHAR(1) COMMENT '장기미사용 지준일 ',

    @Column(length = 50)
    private String samlUrl; //  SAML_URL VARCHAR(50) COMMENT 'SAML 시스템 연결정보',

    @Column(length = 50)
    private String samlPass; // SAML_PASS VARCHAR(50) COMMENT 'SAML 리시스템 PASS',

    @Column(length = 50)
    private String rangerUrl ;//RANGER_URL VARCHAR(50) COMMENT 'RANGER 시스템 연결정보',

    @Column(length = 50)
    private String rangerAuth; //RANGER_PASS VARCHAR(50) COMMENT 'RANGER 시스템 PASS'


    @Builder
    public Gittf0003Entity(Long id, int urCk, String urDay, String samlUrl, String samlPass, String rangerUrl, String rangerAuth) {
        this.id = id;
        this.urCk = urCk;
        this.urDay = urDay;
        this.samlUrl = samlUrl;
        this.samlPass = samlPass;
        this.rangerUrl = rangerUrl;
        this.rangerAuth = rangerAuth;
    }
}
