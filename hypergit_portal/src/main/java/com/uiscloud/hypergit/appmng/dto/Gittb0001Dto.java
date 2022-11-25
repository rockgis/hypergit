package com.uiscloud.hypergit.appmng.dto;

import com.uiscloud.hypergit.appmng.domain.entity.Gittb0001Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittb0001Dto {

    private Long id; //'등록순번'

    private String appSn; //   APP_SN    VARCHAR(50) COMMENT '앱순서',

    private String appNm; // APP_NM    VARCHAR(50) COMMENT '앱명',

    private String clNm ;// CL_NM  VARCHAR(100) COMMENT '클러스터명',

    private String pgeUrlAr ;//PGE_URL_AR  VARCHAR(200) COMMENT '링크 URL',

    private String descTt; //  DESC_TT     VARCHAR(4000) COMMENT '설명',


    private String seTf; // '사용유무',


    private String bsTf; //''기본사용여부''

    private String rgEn;// '등록사번',

    private String altEn;// '등록사번',

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public Gittb0001Entity toEntity(){
        Gittb0001Entity gittb0001Entity = Gittb0001Entity.builder()
                .id(id)
                .appSn(appSn)
                .appNm(appNm)
                .clNm(clNm)
                .pgeUrlAr(pgeUrlAr)
                .descTt(descTt)
                .seTf(seTf)
                .bsTf(bsTf)
                .rgEn(rgEn)
                .altEn(altEn)
                .build();
        return gittb0001Entity;
    }

    @Builder
    public Gittb0001Dto(Long id, String appSn, String appNm, String clNm, String pgeUrlAr, String descTt, String seTf, String bsTf, String rgEn, String altEn,LocalDateTime createdDate, LocalDateTime modifiedDate) {
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
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
