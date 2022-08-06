package com.goodmit.hypergit.inspectionmng.dto;

import com.goodmit.hypergit.inspectionmng.domain.entity.Gittd0001Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittd0001Dto {

    private Long id; //'등록순번'

    private String dcd; // DCD         VARCHAR(50) COMMENT '부서코드',

    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',

    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',

    private String emNm; //EM_NM       CHAR(20) COMMENT '이메일',


    private String usrIp; //USR_IP       CHAR(20) COMMENT '접근 IP ',

    private String ugNm;// UG_NM       CHAR(20) COMMENT '활동',

    private String refUrlAr;// REF_URL_AR  VARCHAR(200) COMMENT 'ReferURL'

    private LocalDateTime createdDate;



    public Gittd0001Entity toEntity(){
        Gittd0001Entity gittd0001Entity = Gittd0001Entity.builder()
                .id(id)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .emNm(emNm)
                .usrIp(usrIp)
                .ugNm(ugNm)
                .refUrlAr(refUrlAr)
                .build();
        return gittd0001Entity;
    }

    @Builder
    public Gittd0001Dto(Long id, String  dcd, String usrNm, String usrEn, String emNm, String usrIp,String ugNm, String refUrlAr,LocalDateTime createdDate) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.usrIp = usrIp;
        this.ugNm = ugNm;
        this.refUrlAr = refUrlAr;
        this.createdDate = createdDate;

    }

}
