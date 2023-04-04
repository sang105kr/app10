package com.kh.app.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadFile {
  private Long uploadfileId;      //  UPLOADFILE_ID	NUMBER(10,0)
  private String code;            //  CODE	VARCHAR2(11 BYTE)
  private Long rid;               //  RID	NUMBER(10,0)
  private String store_filename;  //  STORE_FILENAME	VARCHAR2(50 BYTE)
  private String upload_filename; //  UPLOAD_FILENAME	VARCHAR2(50 BYTE)
  private String fsize;           //  FSIZE	VARCHAR2(45 BYTE)
  private String ftype;           //  FTYPE	VARCHAR2(50 BYTE)
  private LocalDateTime cdate;    //  CDATE	TIMESTAMP(6)
  private LocalDateTime udate;    //  UDATE	TIMESTAMP(6)
}
