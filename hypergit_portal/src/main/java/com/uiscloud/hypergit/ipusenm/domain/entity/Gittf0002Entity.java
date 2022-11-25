package com.uiscloud.hypergit.ipusenm.domain.entity;

//'사용자관리'
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTF0002")
public class Gittf0002Entity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 50, nullable = false)
    private String dcd; // '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; // '사용자명',

    @Column(length = 30, nullable = false)
    private String usrEn ;//'사용자id',


    @Column(length = 50, nullable = false)
    private String nrIpAr; //  NR_IP_AR    VARCHAR(50) COMMENT 'IP주소',

    @Column(length = 4000, nullable = false)
    private String descTt; //  DESC_TT     VARCHAR(4000) COMMENT '설명',

    @Column(columnDefinition ="char" , length = 1, nullable = false)
    private String seTf ;//'SE_TF        CHAR(1) COMMENT '사용유무',

    @Column(length = 30, nullable = false)
    private String rgEn;//  RG_EN   VARCHAR(30) COMMENT '등록사번',

    @Column(length = 30, nullable = false)
    private String altEn;//  ALT_EN  VARCHAR(30) COMMENT '수정사번',


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
    public Gittf0002Entity(Long id, String dcd, String usrNm, String usrEn,  String nrIpAr, String seTf, String descTt, String rgEn, String altEn) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.nrIpAr = nrIpAr;
        this.seTf = seTf;
        this.descTt = descTt;
        this.rgEn = rgEn;
        this.altEn = altEn;
    }
}
