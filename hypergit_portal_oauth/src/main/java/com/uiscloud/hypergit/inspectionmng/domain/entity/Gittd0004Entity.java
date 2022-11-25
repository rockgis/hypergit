package com.uiscloud.hypergit.inspectionmng.domain.entity;

//'사용자관리'

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTD0004")
public class Gittd0004Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 50, nullable = false)
    private String dcd; //    DCD         VARCHAR(50) COMMENT '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',

    @Column(length = 50,nullable = false)
    private String appNm; //    APP_NM    VARCHAR(50) COMMENT '접속 앱명 ',

    @Column(length = 200,  nullable = false)
    private String pgeUrlAr; //PGE_URL_AR  VARCHAR(200) COMMENT '접속 앱 URL',

    @Column( nullable = false)
    private int ugCt;// UG_CT   INT COMMENT '접속상태'

    @Builder
    public Gittd0004Entity(Long id,  String  dcd, String usrNm, String usrEn, String appNm, String pgeUrlAr, int ugCt) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.appNm = appNm;
        this.pgeUrlAr = pgeUrlAr;
        this.ugCt = ugCt;
    }
}
