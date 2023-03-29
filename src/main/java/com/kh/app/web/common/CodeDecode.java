package com.kh.app.web.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeDecode {
  private String code;        //코드
  private String decode;      //디코드
}
