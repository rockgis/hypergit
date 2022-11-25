package com.goodmit.hypergit.rollmn.dto;

import com.goodmit.hypergit.rollmn.domain.entity.Gittf0001Entity;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gittf0001Dto {

    private Long id; //'등록순번'


    private String roleId; // ''역할id'',

    private String roleNm; // ''역할명'',

    private String descTt ;//''설명'',

    private String rgEn;// '등록사번',

    private String altEn;// '등록사번',

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;



    public Gittf0001Entity toEntity(){
        Gittf0001Entity gittf0001Entity = Gittf0001Entity.builder()
                .id(id)
                .roleId(roleId)
                .roleNm(roleNm)
                .descTt(descTt)
                .rgEn(rgEn)
                .altEn(altEn)
                .build();
        return gittf0001Entity;
    }

    @Builder
    public Gittf0001Dto(Long id,String roleId, String roleNm, String descTt, String rgEn, String altEn, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.roleId = roleId;
        this.roleNm = roleNm;
        this.descTt = descTt;
        this.rgEn = rgEn;
        this.altEn = altEn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
