package com.uiscloud.hypergit.appmng.domain.entity;

//'사용자관리'

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTB0001")
public class Gittb0001Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 50, nullable = false)
    private String appSn; //   APP_SN    VARCHAR(50) COMMENT '앱순서',

    @Column(length = 50, nullable = false)
    private String appNm; // APP_NM    VARCHAR(50) COMMENT '앱명',

    @Column(length = 100, nullable = false)
    private String clNm ;// CL_NM  VARCHAR(100) COMMENT '클러스터명',

    @Column(length = 200, nullable = false)
    private String pgeUrlAr ;//PGE_URL_AR  VARCHAR(200) COMMENT '링크 URL',

    @Column(length = 4000, nullable = false)
    private String descTt; //  DESC_TT     VARCHAR(4000) COMMENT '설명',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String seTf; // '사용유무',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String bsTf; //''기본사용여부''

    @Column(length = 30, nullable = false)
    private String rgEn;// '등록사번',

    @Column(length = 30, nullable = false)
    private String altEn;// '등록사번',

    @Builder
    public Gittb0001Entity(Long id, String appSn, String appNm, String clNm, String pgeUrlAr, String descTt, String seTf, String bsTf, String rgEn, String altEn) {
        this.id = id;
        this.appSn = appSn;
        this.appNm = appNm;
        this.clNm = clNm;
        this.pgeUrlAr = pgeUrlAr;
        this.descTt = descTt;
        this.seTf = seTf;
        this.bsTf = bsTf;
        this.rgEn = rgEn;
        this.altEn = altEn;
    }
}
