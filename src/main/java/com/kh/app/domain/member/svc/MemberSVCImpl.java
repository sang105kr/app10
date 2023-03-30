package com.kh.app.domain.member.svc;

import com.kh.app.domain.entity.Member;
import com.kh.app.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  /**
   * 가입
   *
   * @param member
   * @return
   */
  @Override
  public Member save(Member member) {
    return memberDAO.save(member);
  }

  /**
   * @param memberId
   * @param member
   */
  @Override
  public void update(Long memberId, Member member) {

  }

  /**
   * @param email
   * @return
   */
  @Override
  public Optional<Member> findByEmail(String email) {
    return Optional.empty();
  }

  /**
   * @param memberId
   * @return
   */
  @Override
  public Optional<Member> findById(Long memberId) {
    return Optional.empty();
  }

  /**
   * @return
   */
  @Override
  public List<Member> findAll() {
    return null;
  }

  /**
   * @param email
   */
  @Override
  public void delete(String email) {

  }

  /**
   * 회원아이디(email) 존재유무확인
   * @param email
   * @return
   */
  @Override
  public boolean isExist(String email) {
    return memberDAO.isExist(email);
  }

  /**
   * @param email
   * @param passwd
   * @return
   */
  @Override
  public Optional<Member> login(String email, String passwd) {
    return Optional.empty();
  }

  /**
   * @param nickname
   * @return
   */
  @Override
  public Optional<String> findEmailByNickname(String nickname) {
    return Optional.empty();
  }
}
