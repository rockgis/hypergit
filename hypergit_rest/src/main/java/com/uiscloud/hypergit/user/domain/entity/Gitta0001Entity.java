package com.uiscloud.hypergit.user.domain.entity;

//'사용자관리'

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTA0001")
public class Gitta0001Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 50, nullable = false)
    private String dcd; // '   DCD         VARCHAR(50) COMMENT '부서명',',

    @Column(length = 30, nullable = false)
    private String usrNm; // ' USR_NM      VARCHAR(30) COMMENT '사용자명',',

    @Column(length = 30, nullable = false)
    private String usrEn ;//'   USR_EN      VARCHAR(30) COMMENT '사번',',

    @Column(columnDefinition ="char" , length = 20, nullable = false)
    private String emNm; // '    EM_NM       CHAR(20) COMMENT '이메일',',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String adTf ;//'AD_TF        CHAR(1) COMMENT '관리자여부',',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String earEhf; // ' // '  EAR_EHF    CHAR(1)COMMENT '공용계정 USE_YN',',',

    @Column( length = 50, nullable = false)
    private String nrIpAr; //'NR_IP_AR    VARCHAR(50) COMMENT '관리공간_IP','

    @Column(length = 30, nullable = false)
    private String rgEn;// '    RG_EN       VARCHAR(30) COMMENT '등록사번',',




   // }

    @Builder
    public Gitta0001Entity(Long id, String dcd, String usrNm, String usrEn, String emNm, String nrIpAr, String adTf,   String earEhf, String rgEn) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.nrIpAr = nrIpAr;
        this.adTf = adTf;
        this.earEhf = earEhf;
        this.rgEn = rgEn;
    }
}
