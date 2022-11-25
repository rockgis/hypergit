package com.uiscloud.hypergit.user.dto;

import com.uiscloud.hypergit.user.domain.entity.Gitta0002Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gitta0002Dto {


    private Long id; //'등록순번'

    private String usrEn ;//'    USR_EN     VARCHAR(30) COMMENT '사용자 ID',

    private String usTf ;//'   US_TF   CHAR(1) COMMENT '사용구분',


    private String rgEn;     ///RG_EN   VARCHAR(30) COMMENT '소유자 사번',


    private String usrNm; // '     USR_NM      VARCHAR(30) COMMENT '사용자명',


    private int ruleId; // '   RULE_ID  INT COMMENT 'DATA_권한',


    private String seTf; // '  SE_TF    CHAR(1) COMMENT '사용유무',

    private String altEn; //'ALT_EN  VARCHAR(30) COMMENT '수정사번',

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;




    public Gitta0002Entity toEntity(){
        Gitta0002Entity gitta0002Entity = Gitta0002Entity.builder()
                .id(id)
                .usrNm(usrNm)
                .usTf(usTf)
                .rgEn(rgEn)
                .usrEn(usrEn)
                .ruleId(ruleId)
                .seTf(seTf)
                .altEn(altEn)
                .build();
        return gitta0002Entity;
    }

    @Builder
    public Gitta0002Dto(Long id,  String usrNm, String usTf,  String rgEn, String usrEn, int ruleId, String seTf, String altEn ,LocalDateTime createdDate,LocalDateTime modifiedDate) {
        this.id = id;
        this.usrNm = usrNm;
        this.usTf = usTf;
        this.rgEn = rgEn;
        this.usrEn = usrEn;
        this.ruleId = ruleId;
        this.seTf = seTf;
        this.altEn = altEn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
