package com.goodmit.hypergit.inspectionmng.domain.entity;

//'사용자관리'

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTD0003")
public class Gittd0003Entity extends Time1Entity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(columnDefinition ="char" ,length = 20, nullable = false)
    private String wrTy; //   WR_TY       CHAR(20) COMMENT '타입',

    @Column(length = 50, nullable = false)
    private String dcd; //    DCD         VARCHAR(50) COMMENT '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',

    @Column(nullable = false)
    private int ugId; //    UG_ID    int COMMENT '사용권한 부여 이력',

    @Column(length = 30,  nullable = false)
    private String rgEn; //RG_EN   VARCHAR(30) COMMENT '등록사번',

    @Column(length = 30, nullable = false)
    private String altEn;// ALT_EN  VARCHAR(30) COMMENT '수정사번',


    @Builder
    public Gittd0003Entity(Long id, String wrTy, String  dcd, String usrNm, String usrEn,  int ugId, String rgEn, String altEn) {
        this.id = id;
        this.wrTy = wrTy;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.ugId = ugId;
        this.rgEn = rgEn;
        this.altEn = altEn;
    }
}
