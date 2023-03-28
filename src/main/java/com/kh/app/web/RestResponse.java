package com.kh.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse<T> {
  private Header header;
  private T data;

  @Data
  @AllArgsConstructor
  public static class Header {
    private String rtcd;
    private String rtmsg;
  }

  public static <T> RestResponse<T> createRestResponse(String rtcd,String rtmsg, T data){
    return new RestResponse<>(new Header(rtcd,rtmsg),data);
  }
}
