package com.goodmit.hypergit.permissionmng.domain.entity;

//'사용자관리'

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTC0001")
public class Gittc0001Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(nullable = false)
    private int gsnId; //    GSN_ID      INT NOT NULL COMMENT '앱코드',

    @Column(length = 50, nullable = false)
    private String appNm; // APP_NM       VARCHAR(50) COMMENT '앱명',

    @Column(length = 100, nullable = false)
    private String clNm; //CL_NM  VARCHAR(100) COMMENT '클러스터명',

    @Column(length = 200, nullable = false)
    private String pgeUrlAr;   //PGE_URL_AR  VARCHAR(200) COMMENT '링크 URL',

    @Column(length = 50, nullable = false)
    private String roleId ;// ROLE_ID     VARCHAR(50) COMMENT '역할ID',

    @Column(length = 50, nullable = false)
    private String ugId ;//UG_ID       VARCHAR(50) COMMENT '사용자그룹ID',

    @Column(length = 50, nullable = false)
    private String usrDcd; //  USR_DCD         VARCHAR(50) COMMENT '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',

    @Column(columnDefinition ="char" , length = 20,  nullable = false)
    private String emNm; //EM_NM       CHAR(20) COMMENT '이메일',

    @Column(length = 30, nullable = false)
    private String rgEn;// '등록사번',

    @Column(length = 30, nullable = false)
    private String altEn;// '등록사번',

    @Builder
    public Gittc0001Entity(Long id, int gsnId, String appNm, String clNm, String pgeUrlAr,String roleId, String ugId, String usrDcd, String usrNm, String usrEn, String emNm, String rgEn, String altEn) {
        this.id = id;
        this.gsnId = gsnId;
        this.appNm = appNm;
        this.clNm = clNm;
        this.pgeUrlAr = pgeUrlAr;
        this.roleId = roleId;
        this.ugId = ugId;
        this.usrDcd = usrDcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.rgEn = rgEn;
        this.altEn = altEn;
    }
}
