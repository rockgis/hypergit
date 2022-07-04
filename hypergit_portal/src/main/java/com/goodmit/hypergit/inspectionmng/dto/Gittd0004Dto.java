package com.goodmit.hypergit.inspectionmng.dto;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0004Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittd0004Dto {

    private Long id; //'등록순번'

    private String wrTy; //   WR_TY       CHAR(20) COMMENT '타입',


    private String dcd; //    DCD         VARCHAR(50) COMMENT '부서코드',


    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',


    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',


    private String appNm; //    APP_NM    VARCHAR(50) COMMENT '접속 앱명 ',

    private String pgeUrlAr; //PGE_URL_AR  VARCHAR(200) COMMENT '접속 앱 URL',

    private int ugCt;// UG_CT   INT COMMENT '접속상태'


    public Gittd0004Entity toEntity(){
        Gittd0004Entity gittd0004Entity = Gittd0004Entity.builder()
                .id(id)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .appNm(appNm)
                .pgeUrlAr(pgeUrlAr)
                .ugCt(ugCt)
                .build();
        return gittd0004Entity;
    }

    @Builder
    public Gittd0004Dto(Long id,  String  dcd, String usrNm, String usrEn, String appNm, String pgeUrlAr, int ugCt) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.appNm = appNm;
        this.pgeUrlAr = pgeUrlAr;
        this.ugCt = ugCt;
    }

}
