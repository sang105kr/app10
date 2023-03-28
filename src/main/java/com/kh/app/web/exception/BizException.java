package com.kh.app.web.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException{

  private String code;

  public BizException() {
    super();
  }

  public BizException(String message) {
    super(message);
  }

  public BizException(String code, String message) {
    super(message);
    this.code = code;
  }
  public BizException(String message, Throwable cause) {
    super(message, cause);
  }

  public BizException(Throwable cause) {
    super(cause);
  }

  protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
