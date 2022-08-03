package com.goodmit.hypergit.modelmng.dto;

import com.goodmit.hypergit.modelmng.domain.entity.Gittc0001Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittc0001Dto {

    private Long id; //'등록순번'

    private int gsnId; //   GSN_ID      INT NOT NULL COMMENT '앱코드',

    private String appNm; // APP_NM    VARCHAR(50) COMMENT '앱명',

    private String clNm; //CL_NM  VARCHAR(100) COMMENT '클러스터명',

    private String pgeUrlAr;   //PGE_URL_AR  VARCHAR(200) COMMENT '링크 URL',

    private String roleId ;// ROLE_ID     VARCHAR(50) COMMENT '역할ID',

    private String usrDcd; //  DESC_TT     VARCHAR(4000) COMMENT '설명',


    private String usrNm; // USR_EN      VARCHAR(30) COMMENT '사용자ID',


    private String usrEn;

    private String emNm;  //EM_NM       CHAR(20) COMMENT '이메일',

    private String useYn; // USE_YN  CHAR(1) COMMENT '사용유부 \',

    private String rgEn;// '등록사번',

    private String altEn;// '등록사번',

    private String startDate;
    private String endDate;


    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public Gittc0001Entity toEntity(){
        Gittc0001Entity gittc0001Entity = Gittc0001Entity.builder()
                .id(id)
                .gsnId(gsnId)
                .appNm(appNm)
                .clNm(clNm)
                .pgeUrlAr(pgeUrlAr)
                .roleId(roleId)
                .useYn(useYn)
                .usrDcd(usrDcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .emNm(emNm)
                .rgEn(rgEn)
                .altEn(altEn)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return gittc0001Entity;
    }

    @Builder
    public Gittc0001Dto(Long id, int gsnId, String appNm, String clNm, String pgeUrlAr, String roleId, String useYn, String usrDcd, String usrNm, String usrEn, String emNm, String rgEn, String altEn,String startDate,String endDate, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.gsnId = gsnId;
        this.appNm = appNm;
        this.clNm = clNm;
        this.pgeUrlAr = pgeUrlAr;
        this.roleId = roleId;
        this.useYn = useYn;
        this.usrDcd = usrDcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.rgEn = rgEn;
        this.altEn = altEn;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
