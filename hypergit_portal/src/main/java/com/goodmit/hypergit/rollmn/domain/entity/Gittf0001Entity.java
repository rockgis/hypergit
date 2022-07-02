package com.goodmit.hypergit.rollmn.domain.entity;

//'사용자관리'

import com.goodmit.hypergit.board.domain.entity.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITTF0001")
public class Gittf0001Entity extends TimeEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //'등록순번'


    @Column(length = 50, nullable = false)
    private String roleId; // ''역할id'',

    @Column(length = 50, nullable = false)
    private String roleNm; // ''역할명'',

    @Column(length = 40000, nullable = false)
    private String descTt ;//''설명'',

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
    public Gittf0001Entity(Long id, String roleId, String roleNm, String descTt, String rgEn, String altEn) {
        this.id = id;
        this.roleId = roleId;
        this.roleNm = roleNm;
        this.descTt = descTt;
        this.rgEn = rgEn;
        this.altEn = altEn;
    }
}
