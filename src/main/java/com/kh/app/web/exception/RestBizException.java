package com.kh.app.web.exception;

import lombok.Getter;

@Getter
public class RestBizException extends RuntimeException{

  private String code;

  public RestBizException() {
    super();
  }

  public RestBizException(String message) {
    super(message);
  }

  public RestBizException(String code, String message) {
    super(message);
    this.code = code;
  }
  public RestBizException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestBizException(Throwable cause) {
    super(cause);
  }

  protected RestBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
