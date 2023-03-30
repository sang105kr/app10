package com.kh.app.domain.entity;

import lombok.Data;

@Data
public class Code {
  private String codeId;      //  CODE_ID	VARCHAR2(11 BYTE)
  private String decode;      //  DECODE	VARCHAR2(30 BYTE)
  private String detail;      //  detail	CLOB
  private String pcodeId;     //  PCODE_ID	VARCHAR2(11 BYTE)
  private String useyn;    //  USEYN	CHAR(1 BYTE)
//  CDATE	TIMESTAMP(6)
//  UDATE	TIMESTAMP(6)
}
