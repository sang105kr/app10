package com.kh.app.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class StringTest {

  @Test
  void test(){
    List<String> strs = Arrays.asList("축구","등산","골프");
    String join = StringUtils.join(strs, ",");
    log.info("join={}",join);
  }
}
