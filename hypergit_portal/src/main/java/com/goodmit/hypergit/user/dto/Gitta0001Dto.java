package com.goodmit.hypergit.user.dto;

import com.goodmit.hypergit.user.domain.entity.Gitta0001Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Gitta0001Dto {

    private Long id; //'등록순번'

    private String dcd ; // '부서코드',

    private String usrNm ; // '사용자명',

    private String usrEn ;//'사용자id',

    private String usrPin ;//'사용자비밀번호',

    private String emNm; // '이메일',

    private String adTf ;//'관리자여부',

    private String usd;  // '사용자여부',

    private String seTf ; // '사용유무',

    private String earEhf; //'초기설정여부'

    private String rgEn;// '등록사번',

    private String altEn;// '등록사번',

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;



    public Gitta0001Entity toEntity(){
        Gitta0001Entity gitta0001Entity = Gitta0001Entity.builder()
                .id(id)
                .dcd(dcd)
                .usrNm(usrNm)
                .usrEn(usrEn)
                .usrPin(usrPin)
                .emNm(emNm)
                .usd(usd)
                .adTf(adTf)
                .seTf(seTf)
                .earEhf(earEhf)
                .rgEn(rgEn)
                .altEn(altEn)
                .build();
        return gitta0001Entity;
    }

    @Builder
    public Gitta0001Dto(Long id, String dcd, String usrNm, String usrEn, String usrPin, String emNm, String adTf, String usd, String seTf, String earEhf, String rgEn, String altEn, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.usrPin = usrPin;
        this.emNm = emNm;
        this.usd = usd;
        this.adTf = adTf;
        this.seTf = seTf;
        this.earEhf = earEhf;
        this.rgEn = rgEn;
        this.altEn = altEn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
