package com.goodmit.hypergit.inspectionmng.dto;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0003Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittd0003Dto {

    private Long id; //'등록순번'

    private String wrTy; //   WR_TY       CHAR(20) COMMENT '타입',


    private String dcd; //    DCD         VARCHAR(50) COMMENT '부서코드',


    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',


    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',


    private int ugId; //    UG_ID    int COMMENT '사용권한 부여 이력',

    private String rgEn; //RG_EN   VARCHAR(30) COMMENT '등록사번',

    private String altEn;// ALT_EN  VARCHAR(30) COMMENT '수정사번',

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public Gittd0003Entity toEntity(){
        Gittd0003Entity gittd0003Entity = Gittd0003Entity.builder()
                .id(id)
                .wrTy(wrTy)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .ugId(ugId)
                .rgEn(rgEn)
                .altEn(altEn)
                .build();
        return gittd0003Entity;
    }

    @Builder
    public Gittd0003Dto(Long id, String wrTy, String  dcd, String usrNm, String usrEn,  int ugId, String rgEn, String altEn,LocalDateTime createdDate ,LocalDateTime modifiedDate) {
        this.id = id;
        this.wrTy = wrTy;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.ugId = ugId;
        this.rgEn = rgEn;
        this.altEn = altEn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
