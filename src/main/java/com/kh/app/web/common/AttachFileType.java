package com.kh.app.web.common;

public enum AttachFileType {

  F010301("상품첨부파일"),
  F010302("상품이미지파일");

  private String description;

  AttachFileType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
