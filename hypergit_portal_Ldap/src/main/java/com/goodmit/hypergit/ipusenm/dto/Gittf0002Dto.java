package com.goodmit.hypergit.ipusenm.dto;

import com.goodmit.hypergit.ipusenm.domain.entity.Gittf0002Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittf0002Dto {

    private Long id; //'등록순번'

    private String dcd; // '부서코드',

    private String usrNm; // '사용자명',

    private String usrEn ;//'사용자id',

    private String nrIpAr; //  NR_IP_AR    VARCHAR(50) COMMENT 'IP주소',

    private String descTt; //  DESC_TT     VARCHAR(4000) COMMENT '설명',

    private String seTf ;//'SE_TF        CHAR(1) COMMENT '사용유무',

    private String rgEn;//  RG_EN   VARCHAR(30) COMMENT '등록사번',

    private String altEn;//  ALT_EN  VARCHAR(30) COMMENT '수정사번',

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;



    public Gittf0002Entity toEntity(){
        Gittf0002Entity gitta0001Entity = Gittf0002Entity.builder()
                .id(id)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .nrIpAr(nrIpAr)
                .seTf(seTf)
                .descTt(descTt)
                .rgEn(rgEn)
                .altEn(altEn)
                .build();
        return gitta0001Entity;
    }

    @Builder
    public Gittf0002Dto(Long id, String dcd, String usrNm, String usrEn, String nrIpAr, String seTf, String descTt, String rgEn, String altEn, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.nrIpAr = nrIpAr;
        this.seTf = seTf;
        this.descTt = descTt;
        this.rgEn = rgEn;
        this.altEn = altEn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
