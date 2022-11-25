package com.uiscloud.hypergit.logmng.domain.entity;

//'사용자관리'
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTE0001")
public class Gitte0001Entity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'

    @Column(length = 30, nullable = false)
    private String dcd; // DCD         VARCHAR(30) COMMENT '부서코드',

    @Column(length = 30, nullable = false)
    private String usrNm; //  USR_NM      VARCHAR(30) COMMENT '성명',

    @Column(length = 30, nullable = false)
    private String usrEn; // USR_EN      VARCHAR(30) COMMENT '사번',

    @Column( length = 200, nullable = false)
    private String reqNm;//  REQ_NM      VARCHAR(200) COMMENT '연동 정보',

    @Column(columnDefinition ="char" , length = 1, nullable = false)
    private String mnuId;//   MNU_ID      CHAR(1) COMMENT '결과',

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
    public Gitte0001Entity(Long id, String dcd, String usrNm, String usrEn, String reqNm, String mnuId) {
        this.id = id;
        this.dcd = dcd;
        this.usrNm = usrNm;
        this.usrEn = usrEn;
        this.reqNm = reqNm;
        this.mnuId = mnuId;
    }
}
