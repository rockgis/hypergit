package com.goodmit.hypergit.user.dto;

import com.goodmit.hypergit.user.domain.entity.Gitta0001Entity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gitta0001Dto {


    private Long id; //'등록순번'


    private String dcd; // '   DCD         VARCHAR(50) COMMENT '부서명',',


    private String usrNm; // ' USR_NM      VARCHAR(30) COMMENT '사용자명',',


    private String usrEn ;//'   USR_EN      VARCHAR(30) COMMENT '사번',',


    private String emNm; // '    EM_NM       CHAR(20) COMMENT '이메일',',

    private String nrIpAr; //'NR_IP_AR    VARCHAR(50) COMMENT '관리공간_IP','


    private String adTf ;//'AD_TF        CHAR(1) COMMENT '관리자여부',',


    private String earEhf; // ' // '  EAR_EHF    CHAR(1)COMMENT '공용계정 USE_YN',',',


    private String rgEn;// '    RG_EN       VARCHAR(30) COMMENT '등록사번',',

    private LocalDateTime createdDate;




    public Gitta0001Entity toEntity(){
        Gitta0001Entity gitta0001Entity = Gitta0001Entity.builder()
                .id(id)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .emNm(emNm)
                .nrIpAr(nrIpAr)
                .adTf(adTf)
                .earEhf(earEhf)
                .rgEn(rgEn)
                .build();
        return gitta0001Entity;
    }

    @Builder
    public Gitta0001Dto(Long id, String dcd, String usrNm, String usrEn,  String emNm, String adTf, String nrIpAr, String earEhf, String rgEn, LocalDateTime createdDate) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.nrIpAr = nrIpAr;
        this.adTf = adTf;
        this.earEhf = earEhf;
        this.rgEn = rgEn;
        this.createdDate = createdDate;
    }
}
