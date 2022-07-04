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
@Table(name = "GITTA0001")
public class Gitta0001Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 50, nullable = false)
    private String dcd; // '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn ;//'사용자id',

    @Column(length = 30, nullable = false)
    private String usrPin ;//'사용자비밀번호',

    @Column(columnDefinition ="char" , length = 20, nullable = false)
    private String emNm; // '이메일',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String adTf ;//'관리자여부',

    @Column(length = 50, nullable = false)
    private String usd;  // '사용자여부',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String seTf; // '사용유무',

    @Column( columnDefinition ="char" , length = 1, nullable = false)
    private String earEhf; //'초기설정여부'

    @Column(length = 30, nullable = false)
    private String rgEn;// '등록사번',

    @Column(length = 30, nullable = false)
    private String altEn;// '등록사번',

   // @Transient
   // private String strRg_Dt = "";

   // public String getStrRg_Dt() {
  //      return  new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(this.rg_dt);
  //  }

  //  @Transient
  //  private String strAlt_Dt = "";

  //  public String getStrAlt_Dt() {
   //     return  new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(this.alt_dt);
   // }

    @Builder
    public Gitta0001Entity(Long id, String dcd, String usrNm, String usrEn, String usrPin, String emNm, String adTf, String usd, String seTf, String earEhf, String rgEn, String altEn) {
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
    }
}
