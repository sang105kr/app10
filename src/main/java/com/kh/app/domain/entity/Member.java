package com.kh.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  private Long memberId;        //  MEMBER_ID	NUMBER
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String passwd;        //  PASSWD	VARCHAR2(12 BYTE)
  private String nickname;      //  NICKNAME	VARCHAR2(30 BYTE)
  private String gender;        //  GENDER	VARCHAR2(6 BYTE)
  private String hobby;         //  HOBBY	VARCHAR2(300 BYTE)
  private String region;        //  REGION	VARCHAR2(30 BYTE)
  private String gubun;         //  GUBUN	VARCHAR2(11 BYTE)
  private byte[] pic;           //  PIC	BLOB
//  private LocalDateTime cdate;      //  CDATE	TIMESTAMP(6)
//  private LocalDateTime udate;        //  UDATE	TIMESTAMP(6)

  public Member(Long memberId, String email, String passwd) {
    this.memberId = memberId;
    this.email = email;
    this.passwd = passwd;
  }
}
