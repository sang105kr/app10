package com.kh.app.web;

import com.kh.app.web.exception.RestBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = RestProductController.class)
public class RestExHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RestBizException.class)
  public RestResponse<Object> BizExHandle(RestBizException e) {
    return RestResponse.createRestResponse(e.getCode(),e.getMessage(),null);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public RestResponse<Object> BizExHandle(Exception e) {
    return RestResponse.createRestResponse("99",e.getMessage(),null);
  }
}
