package com.kh.app.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MeasuringInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    request.setAttribute("beginTime",System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    long beginTime = (long)request.getAttribute("beginTime");
    long endTime = System.currentTimeMillis();
    log.info(request.getRequestURI()+"실행시간:"+(endTime-beginTime));
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
