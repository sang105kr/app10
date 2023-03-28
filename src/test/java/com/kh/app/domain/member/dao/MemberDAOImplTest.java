package com.kh.app.domain.member.dao;

import com.kh.app.domain.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberDAOImplTest {

  @Autowired
  private MemberDAO memberDAO;

  @Test
  @DisplayName("가입")
  void save() {
    Member member = new Member();
    member.setEmail("test9@kh.com");
    member.setPasswd("1234");
    member.setNickname("별칭9");
    member.setGender("남자");
    member.setHobby("골프");
    member.setRegion("울산");

    Member savedMember = memberDAO.save(member);

    Assertions.assertThat(savedMember.getMemberId()).isGreaterThan(0);
    Assertions.assertThat(savedMember.getEmail()).isEqualTo("test9@kh.com");
    Assertions.assertThat(savedMember.getPasswd()).isEqualTo("1234");
    Assertions.assertThat(savedMember.getNickname()).isEqualTo("별칭9");
    Assertions.assertThat(savedMember.getGender()).isEqualTo("남자");
    Assertions.assertThat(savedMember.getHobby()).isEqualTo("골프");
    Assertions.assertThat(savedMember.getRegion()).isEqualTo("울산");
  }
}