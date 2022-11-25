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
@Table(name = "GITTD0002")
public class Gittd0002Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(columnDefinition ="char" ,length = 20, nullable = false)
    private String ugNm; //  UG_NM       CHAR(20) COMMENT '상태',

    @Column(length = 50, nullable = false)
    private String dcd; //    DCD         VARCHAR(50) COMMENT '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',

    @Column(length = 30, nullable = false)
    private String ugId; //    UG_ID      VARCHAR(50) COMMENT '사용자그룹ID',

    @Column(columnDefinition ="char" , length = 20,  nullable = false)
    private String usrIp; //USR_IP       CHAR(20) COMMENT '접근 IP',

    @Column(columnDefinition ="char" , length = 20, nullable = false)
    private String emNm;// EM_NM       CHAR(20) COMMENT '이메일',

    @Column(columnDefinition ="char" ,length = 20, nullable = false)
    private String lfDt;// LF_DT       CHAR(20) COMMENT '마지막로그인실패시간'


    @Builder
    public Gittd0002Entity(Long id, String ugNm, String  dcd, String usrNm, String usrEn, String emNm, String ugId, String usrIp, String lfDt) {
        this.id = id;
        this.ugNm = ugNm;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.ugId = ugId;
        this.usrIp = usrIp;
        this.lfDt = lfDt;
    }
}
