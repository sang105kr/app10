package com.kh.app.domain.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MailServiceTest {

  @Autowired
  private MailService mailService;

  @Test
  void sendSimpleMail() {
    mailService.sendMail("sang105kr@gmail.com","test","test");
  }
}