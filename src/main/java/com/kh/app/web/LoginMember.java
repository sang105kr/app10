package com.kh.app.web;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 회원 세션 정보
 */
@Data
@AllArgsConstructor
public class LoginMember {
  private Long memberId;
  private String email;
  private String nickname;
  private String gubun;
}
