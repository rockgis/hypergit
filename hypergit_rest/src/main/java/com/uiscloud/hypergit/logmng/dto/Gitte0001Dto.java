package com.uiscloud.hypergit.logmng.dto;

import com.uiscloud.hypergit.logmng.domain.entity.Gitte0001Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gitte0001Dto {

    private Long id; //'등록순번'


    private String dcd; // ''역할id'',

    private String usrNm; // ''역할명'',

    private String usrEn ;//''설명'',


    private String reqNm;// '등록사번',

    private String mnuId;// '등록사번',

    private LocalDateTime createdDate;



    public Gitte0001Entity toEntity(){
        Gitte0001Entity gitta0001Entity = Gitte0001Entity.builder()
                .id(id)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .reqNm(reqNm)
                .mnuId(mnuId)
                .build();
        return gitta0001Entity;
    }

    @Builder
    public Gitte0001Dto(Long id, String dcd, String usrNm, String usrEn, String reqNm, String mnuId ,LocalDateTime createdDate) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.reqNm = reqNm;
        this.mnuId = mnuId;
        this.createdDate = createdDate;
    }
}
