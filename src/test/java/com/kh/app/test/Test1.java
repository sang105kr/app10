package com.kh.app.test;


import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.dao.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class Test1 {
  @Autowired
  private MemberDAO memberDAO;


  @Test
  @DisplayName("취미")
    void test1(){
    List<Member> all = memberDAO.findAll();
    List<String> collect = all.stream().map(m -> m.getHobby()).collect(Collectors.toList());
    log.info("collect={}", collect);
    Map<String, List<Member>> collect1 = all.stream().collect(Collectors.groupingBy(m -> m.getGender()));
    log.info("collect1={}", collect1);
  }
}
