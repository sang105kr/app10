package com.kh.app.domain.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PasswordGeneratorCreatorTest {

  @Test
  void generator() {
    String tmpPwd = PasswordGeneratorCreator.generator(10);
    log.info("tmpPwd={}", tmpPwd);
  }

  @Test
  void generator2() {
    PasswordGenerator.PasswordGeneratorBuilder passwordGeneratorBuilder = new PasswordGenerator.PasswordGeneratorBuilder();
    String pwd = passwordGeneratorBuilder
        .useDigits(true)
        .useLower(true)
        .useUpper(true)
        .usePunctuation(false)
        .build()
        .generate(10);
    log.info("pw={}",pwd);

  }

  @Test
  void uuid(){
    UUID uuid = UUID.randomUUID();
    log.info("tmp={}",uuid.toString().substring(0,10));
  }
}