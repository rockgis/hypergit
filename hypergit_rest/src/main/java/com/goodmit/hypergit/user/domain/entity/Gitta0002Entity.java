package com.goodmit.hypergit.user.domain.entity;

//'사용자관리'

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTA0002")
public class Gitta0002Entity extends TimeEntity01  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 30, nullable = false)
    private String usrEn ;//'    USR_EN     VARCHAR(30) COMMENT '사용자 ID',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String usTf ;//'   US_TF   CHAR(1) COMMENT '사용구분',

    @Column(length = 30, nullable = false)
    private String rgEn;     ///RG_EN   VARCHAR(30) COMMENT '소유자 사번',

    @Column(length = 30, nullable = false)
    private String usrNm; // '     USR_NM      VARCHAR(30) COMMENT '사용자명',



    @Column( nullable = false)
    private int ruleId; // '   RULE_ID  INT COMMENT 'DATA_권한',


    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String seTf; // '  SE_TF    CHAR(1) COMMENT '사용유무',

    @Column( length = 50, nullable = false)
    private String altEn; //'ALT_EN  VARCHAR(30) COMMENT '수정사번',


   // }

    @Builder
    public Gitta0002Entity(Long id, String usrEn, String usTf, String rgEn , String usrNm,  int ruleId,  String seTf, String altEn) {
        this.id = id;
        this.usrEn = usrEn;
        this.usTf = usTf;
        this.rgEn = rgEn;
        this.usrNm = usrNm;
        this.ruleId = ruleId;
        this.seTf = seTf;
        this.altEn =  altEn;

    }
}
