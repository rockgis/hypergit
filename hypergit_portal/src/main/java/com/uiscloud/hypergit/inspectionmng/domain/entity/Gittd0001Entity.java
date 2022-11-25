package com.uiscloud.hypergit.inspectionmng.domain.entity;

//'사용자관리'

import com.uiscloud.hypergit.inspectionmng.domain.entity.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTD0001")
public class Gittd0001Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 50, nullable = false)
    private String dcd; // DCD         VARCHAR(50) COMMENT '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // USR_NM      VARCHAR(30) COMMENT '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn; //USR_EN      VARCHAR(30) COMMENT '사용자ID',

    @Column(columnDefinition ="char" , length = 20,  nullable = false)
    private String emNm; //EM_NM       CHAR(20) COMMENT '이메일',

    @Column(columnDefinition ="char" , length = 20,  nullable = false)
    private String usrIp; //USR_IP       CHAR(20) COMMENT '접근 IP ',


    @Column(columnDefinition ="char" , length = 20, nullable = false)
    private String ugNm;// UG_NM       CHAR(20) COMMENT '활동',

    @Column(length = 200, nullable = false)
    private String refUrlAr;// REF_URL_AR  VARCHAR(200) COMMENT 'ReferURL'

    @Builder
    public Gittd0001Entity(Long id, String  dcd, String usrNm, String usrEn, String emNm, String usrIp,String ugNm, String refUrlAr) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.emNm = emNm;
        this.usrIp = usrIp;
        this.ugNm = ugNm;
        this.refUrlAr = refUrlAr;
    }
}
