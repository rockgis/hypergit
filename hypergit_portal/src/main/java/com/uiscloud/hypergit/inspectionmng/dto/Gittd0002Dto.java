package com.uiscloud.hypergit.inspectionmng.dto;

import com.uiscloud.hypergit.inspectionmng.domain.entity.Gittd0002Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittd0002Dto {

    private Long id; //'등록순번'

    private String ugNm; //  WR_TY       CHAR(20) COMMENT '상태',
    private String dcd; //    DCD         VARCHAR(50) COMMENT '부서코드',
    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',
    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',
    private String ugId; //    UG_ID      VARCHAR(50) COMMENT '사용자그룹ID',
    private String usrIp; //USR_IP       CHAR(20) COMMENT '접근 IP',

    private String emNm;// EM_NM       CHAR(20) COMMENT '이메일',

    private String lfDt;// LF_DT       CHAR(20) COMMENT '마지막로그인실패시간'

    private LocalDateTime createdDate;


    public Gittd0002Entity toEntity(){
        Gittd0002Entity gittd0002Entity = Gittd0002Entity.builder()
                .id(id)
                .ugNm(ugNm)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .emNm(emNm)
                .ugId(ugId)
                .usrIp(usrIp)
                .lfDt(lfDt)
                .build();
        return gittd0002Entity;
    }

    @Builder
    public Gittd0002Dto(Long id, String ugNm, String  dcd, String usrNm, String usrEn, String emNm, String ugId, String usrIp, String lfDt,LocalDateTime createdDate) {
        this.id = id;
        this.ugNm = ugNm;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.ugId = ugId;
        this.usrIp = usrIp;
        this.lfDt = lfDt;
        this.createdDate = createdDate;

    }

}
