package com.kh.app.domain.member.dao;

import com.kh.app.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDAO {
  /**
   * 가입
   * @param member
   * @return
   */
  Member save(Member member);

  //수정
  void update(Long memberId, Member member);

  //조회 by mail
  Optional<Member> findByEmail(String email);

  //조회 by member_id
  Optional<Member> findById(Long memberId);

  //전체조회
  List<Member> findAll();

  //탈퇴
  void delete(String email);

  //회원유무
  boolean isExist(String email);

  //로그인
  Optional<Member> login(String email, String passwd);

  //아이디찾기
  Optional<String> findEmailByNickname(String nickname);

  //비밀번호찾기
  boolean isExistByEmailAndNickname(String email, String nickname);

  //비밀번호변경
  void changePasswd(String email, String passwd);

}
